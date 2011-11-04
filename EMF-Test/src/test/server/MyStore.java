package test.server;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.ISession;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.ITransaction;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.spi.server.Store;
import org.eclipse.emf.cdo.spi.server.StoreAccessorPool;

public class MyStore extends Store {
	public static final String TYPE = "my"; //$NON-NLS-1$

	public MyStore() {
	    super(TYPE, null,
	    		set(ChangeFormat.REVISION, ChangeFormat.DELTA),
	            set(RevisionTemporality.AUDITING, RevisionTemporality.NONE),
	            set(RevisionParallelism.NONE, RevisionParallelism.BRANCHING));
	}
	
	@Override
	public boolean isLocal(CDOID id) {
		System.out.println("isLocal(CDOID id)");
		return false;
	}

	@Override
	public void setCreationTime(long creationTime) {
		System.out.println("setCreationTime(long creationTime)");
		
	}

	@Override
	public CDOID createObjectID(String val) {
		System.out.println("createObjectID(String val)");
		return null;
	}

	@Override
	public boolean isFirstStart() {
		System.out.println("isFirstStart()");
		return false;
	}

	@Override
	public long getCreationTime() {
		System.out.println("getCreationTime()");
		return 0;
	}

	@Override
	public Map<String, String> getPersistentProperties(Set<String> names) {
		System.out.println("getPersistentProperties(Set<String> names)");
		return null;
	}

	@Override
	public void setPersistentProperties(Map<String, String> properties) {
		System.out.println("setPersistentProperties(Map<String, String> properties)");
		
	}

	@Override
	public void removePersistentProperties(Set<String> names) {
		System.out.println("removePersistentProperties(Set<String> names)");
		
	}

	@Override
	protected StoreAccessorPool getReaderPool(ISession session, boolean forReleasing) {
		System.out.println("getReaderPool(ISession session, boolean forReleasing): " + session + ": " + forReleasing);
		return null;
	}

	@Override
	protected StoreAccessorPool getWriterPool(IView view, boolean forReleasing) {
		System.out.println("getWriterPool(IView view, boolean forReleasing): " + view + ": "+ forReleasing);
		return null;
	}

	@Override
	protected IStoreAccessor createReader(ISession session) {
		System.out.println("createReader(ISession session): " + session);
		return null;
	}

	@Override
	protected IStoreAccessor createWriter(ITransaction transaction) {
		System.out.println("createWriter(ITransaction transaction)");
		return null;
	}

}
