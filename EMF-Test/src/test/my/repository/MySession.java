package test.my.repository;

import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EStructuralFeature;

import test.domain.Entity;

public class MySession {
	
	//TODO: entity cache !!! (search by id)
	//private HashMap<String, Entity> entities = new HashMap<String, Entity>();
	
	private final Adapter eAdapter = new AdapterImpl() {
		public void notifyChanged(Notification notification) {
			EStructuralFeature feature = (EStructuralFeature)notification.getFeature();
			
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
	
	
	public MySession() {
		MyContext.getData().newTransaction(this);
	}
	
	public void persist(Entity entity) {
		if(entity.getId() == null) {
			entity.setId(UUID.randomUUID().toString());
			entity.eAdapters().add(eAdapter);
			MyContext.getData().getTransaction().newEntity(entity);
		} else if(!entity.eAdapters().contains(eAdapter)) { //ID not null, but contains adapter!!! ID was changed manually!
			throw new RuntimeException("Do not set ID's for new entities! ID's are created automatic. " +
					"If the ID is not set by you, maybe your entity is deleted.");
		}
	}
	
	public void delete(Entity entity) {
		if(entity.getId() != null) {
			MyContext.getData().getTransaction().deleteEntity(entity);
			entity.eAdapters().remove(eAdapter);
		}
	}
	
	public void commit() {
		//TODO: send to server => if OK => commit local data => additional changes reported by the server => (id, ...)
		System.out.println("COMMIT: ");
		System.out.println(MyContext.getData().getTransaction().getChangeResume());
		
		MyContext.getData().newTransaction(this);
	}
	
	public void rollback() {
		MyContext.getData().getTransaction().revert();	//revert all changes in the model
		MyContext.getData().newTransaction(this);
	}
	
	public void close() {
		
	}
}
