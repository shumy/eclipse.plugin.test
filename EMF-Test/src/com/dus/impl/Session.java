package com.dus.impl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.dus.IRepository;
import com.dus.ISession;
import com.dus.spi.context.Context;
import com.dus.spi.router.IQueryRouter;
import com.dus.spi.router.ITransactionRouter;

import test.domain.Entity;

public class Session implements ISession {
	
	private final Adapter eAdapter = new AdapterImpl() {
		public void notifyChanged(Notification notification) {
			EStructuralFeature feature = (EStructuralFeature)notification.getFeature();
			if(feature == null) return;
			
			if(feature.getName().equals("id"))
				throw new RuntimeException("Changing ID from an Entity is not allowed! ID's are created automatic.");
			
			Context.getData().getTransaction().addChange(
					(Entity)notification.getNotifier(),
					feature,
					notification.getEventType(),
					notification.getOldValue(),
					notification.getNewValue(),
					notification.getPosition());
		}
	};
	
	private final ITransactionRouter tRouter;
	private final IQueryRouter qRouter;
	
	public Session(ITransactionRouter tRouter, IQueryRouter qRouter) {
		this.tRouter = tRouter;
		this.qRouter = qRouter;
		Context.getData().newTransaction(this);
	}
	
	public void activateAdapter(Entity entity) {
		entity.eAdapters().add(eAdapter);
	}
	
	public void deactivateAdapter(Entity entity) {
		entity.eAdapters().remove(eAdapter);
	}
	
	public ITransactionRouter getTransactionRouter() {return tRouter;}
	
	public IQueryRouter getQueryRouter() {return qRouter;}
	
	@Override
	public void persist(Entity entity) {
		if(entity.getId() == null) {
			entity.setId("TMP" + EcoreUtil.generateUUID());
			entity.eAdapters().add(eAdapter);
			Context.getData().getTransaction().newEntity(entity);
		} else if(!entity.eAdapters().contains(eAdapter)) { //ID not null, but contains adapter!!! ID was changed manually!
			throw new RuntimeException("Do not set ID's for new entities! ID's are created automatic. " +
					"If the ID is not set by you, maybe your entity is deleted.");
		}
	}
	
	@Override
	public void delete(Entity entity) {
		if(entity.getId() != null) {
			entity.eAdapters().remove(eAdapter);
			Context.getData().getTransaction().deleteEntity(entity);
		}
	}
	
	@Override
	public void commit() {
		if(Context.getData().getTransaction().commit())
			Context.getData().newTransaction(this);
	}
	
	@Override
	public void rollback() {
		Context.getData().getTransaction().revert();	//revert all changes in the model
		Context.getData().newTransaction(this);
	}
	
	@Override
	public void close() {
		
	}
	
	@Override
	public IRepository getRepository(EPackage ePackage) {		
		return new Repository(this, ePackage);
	}
}
