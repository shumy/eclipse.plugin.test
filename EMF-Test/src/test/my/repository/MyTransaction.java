package test.my.repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MyTransaction {
	
	public static class Change {
		public final int type;
		public final EObject entity;
		public final EStructuralFeature feature;
		
		public final Object oldValue;
		public final Object newValue;
		public final int listIndex;
		
		public Change(int type, EObject entity, EStructuralFeature feature, Object oldValue, Object newValue, int listIndex) {
			this.type = type;
			this.entity = entity;
			this.feature = feature;
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.listIndex = listIndex;
		}
		
		@Override
		public String toString() {
			String sType = "";
			switch (type) {
				case Notification.SET: sType = "SET"; break;
				case Notification.UNSET: sType = "UNSET"; break;
				case Notification.ADD: sType = "ADD"; break;
				case Notification.REMOVE: sType = "REMOVE"; break;
			}
			
			return "Type=" + sType +
			", Entity=" + entity.eClass().getName() + "@" + entity.hashCode() +
			", Field=" + feature.getName() +
			", OldValue=" + oldValue + 
			", NewValue=" + newValue;
		}
	}
	
	private final MySession session;
	
	private final String txId = UUID.randomUUID().toString();
	private final LinkedList<Change> changes = new LinkedList<MyTransaction.Change>();
	
	MyTransaction(MySession session) {
		this.session = session;
	}
	
	String getId() {return txId;}
	
	
	void addChange(EObject entity, EStructuralFeature feature, int type, Object oldValue, Object newValue, int listIndex) {
		changes.add(new Change(type, entity, feature, oldValue, newValue, listIndex));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void revertAll() {
		Iterator<Change> iter = changes.descendingIterator();
		while(iter.hasNext()) {
			Change ch = iter.next(); 
			ch.entity.eSetDeliver(false);
			switch (ch.type) {
				case Notification.SET:
				case Notification.UNSET:
					ch.entity.eSet(ch.feature, ch.oldValue);
					break;
				
				case Notification.ADD:
					((EList)ch.entity.eGet(ch.feature)).remove(ch.listIndex);
					break;
				case Notification.REMOVE:
					((EList)ch.entity.eGet(ch.feature)).add(ch.listIndex, ch.oldValue);
					break;
			}
			ch.entity.eSetDeliver(true);
		}
	}
}
