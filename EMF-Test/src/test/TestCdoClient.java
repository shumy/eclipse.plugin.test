package test;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.net4j.CDOSessionConfiguration;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleEventAdapter;

import test.domain.Address;
import test.domain.DomainFactory;
import test.domain.User;

public class TestCdoClient {

	private static CDOSession cdoSession;

	public static void init() {
		// The following lines are not needed if the extension
		// registry (OSGi/Equinox) is running
		Net4jUtil.prepareContainer(IPluginContainer.INSTANCE);
		TCPUtil.prepareContainer(IPluginContainer.INSTANCE);
	}

	public static CDOSession openSession(String repoName) {
		final IConnector connector = TCPUtil.getConnector(IPluginContainer.INSTANCE, "localhost");
		
		CDOSessionConfiguration config = CDONet4jUtil.createSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(repoName);

		CDOSession session = config.openSession();

		session.addListener(new LifecycleEventAdapter() {
			@Override
			protected void onDeactivated(ILifecycle lifecycle) {
				connector.close();
			}
		});

		return session;
	}

	public static void main(String[] args) {
		init();
		cdoSession = openSession("demo");
		
		try {
			DomainFactory dFactory = DomainFactory.eINSTANCE;
			
			CDOTransaction transaction = cdoSession.openTransaction();
			
				Address address = dFactory.createAddress();
				address.setLocal("Aveiro");
	
				User user = dFactory.createUser();
				user.setName("Micael");
				user.setPass("password");
				user.getAddresses().add(address);

			CDOResource resource = transaction.getOrCreateResource("/myResource");
			resource.getContents().add(user);
			
			transaction.commit();
		} catch (CommitException e) {
			e.printStackTrace();
		} finally {
			cdoSession.close();
		}
	}

}
