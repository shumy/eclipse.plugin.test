package test.my.repository;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MySession {
	
	private final Adapter eAdapter = new AdapterImpl() {
		public void notifyChanged(Notification notification) {			
			MyContext.getData().getTransaction().addChange(
					(EObject)notification.getNotifier(),
					(EStructuralFeature)notification.getFeature(),
					notification.getEventType(),
					notification.getOldValue(),
					notification.getNewValue());
		}
	};
	
	
	public MySession() {
		MyContext.getData().newTransaction(this);
	}
	
	public void persist(EObject entity) {
		if(!entity.eAdapters().contains(eAdapter))
			entity.eAdapters().add(eAdapter);
	}
	
	public void commit() {
		//TODO: send to server
		
		//TODO: if OK => commit local data
		
		//reset transaction resource
		MyContext.getData().newTransaction(this);
	}
	
	public void rollback() {
		//TODO: revert changes!!!
		
		//reset transaction resource
		MyContext.getData().getTransaction().revertAll();
		MyContext.getData().newTransaction(this);
	}
	
	public void close() {
		
	}
}
