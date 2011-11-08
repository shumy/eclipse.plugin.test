package test.my.impl.repository;

import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EStructuralFeature;

import test.domain.Entity;
import test.my.ISession;
import test.my.spi.IRouter;
import test.my.spi.context.MyContext;

public class MySession implements ISession {
	
	private final Adapter eAdapter = new AdapterImpl() {
		public void notifyChanged(Notification notification) {
			EStructuralFeature feature = (EStructuralFeature)notification.getFeature();
			if(feature == null) return;
			
			if(feature.getName().equals("id"))
				throw new RuntimeException("Changing ID from an Entity is not allowed! ID's are created automatic.");
			
			MyContext.getData().getTransaction().addChange(
					(Entity)notification.getNotifier(),
					feature,
					notification.getEventType(),
					notification.getOldValue(),
					notification.getNewValue(),
					notification.getPosition());
		}
	};
	
	private final IRouter router;
	
	public MySession(IRouter router) {
		this.router = router;
		MyContext.getData().newTransaction(this);
	}
	
	@Override
	public void persist(Entity entity) {
		if(entity.getId() == null) {
			entity.setId("tmp-" + UUID.randomUUID().toString());
			entity.eAdapters().add(eAdapter);
			MyContext.getData().getTransaction().newEntity(entity);
		} else if(!entity.eAdapters().contains(eAdapter)) { //ID not null, but contains adapter!!! ID was changed manually!
			throw new RuntimeException("Do not set ID's for new entities! ID's are created automatic. " +
					"If the ID is not set by you, maybe your entity is deleted.");
		}
	}
	
	@Override
	public void delete(Entity entity) {
		if(entity.getId() != null) {
			entity.eAdapters().remove(eAdapter);
			MyContext.getData().getTransaction().deleteEntity(entity);
		}
	}
	
	@Override
	public void commit() {
		if(MyContext.getData().getTransaction().commit(router))
			MyContext.getData().newTransaction(this);
	}
	
	@Override
	public void rollback() {
		MyContext.getData().getTransaction().revert();	//revert all changes in the model
		MyContext.getData().newTransaction(this);
	}
	
	@Override
	public void close() {
		
	}
}
