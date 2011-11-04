package test.server;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;

public class ServerTest {

	private static final String DEFAULT_REPOSITORY_NAME = "demo";
	
	public static void main(String[] args) throws Exception {
		OMPlatform.INSTANCE.setDebugging(true);
		//OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
		
		IManagedContainer container = ContainerUtil.createContainer();
		
		try {
			Net4jUtil.prepareContainer(container);
			TCPUtil.prepareContainer(container);
			CDONet4jUtil.prepareContainer(container);
			CDONet4jServerUtil.prepareContainer(container);
			LifecycleUtil.activate(container);
			
			CDOServerUtil.addRepository(container, getRepository(getStore()));
			container.registerFactory(new ProtocolFactory());
			
			
			IAcceptor acceptor = TCPUtil.getAcceptor(container, "0.0.0.0:2036");
			System.out.println("Accepting connections: " + acceptor);
			
			while(System.in.read() == -1) {
				Thread.sleep(200);
			}
			
			
		} finally {
			LifecycleUtil.deactivate(container);
		}

	}
	
	private static IStore getStore() {
		return new MyStore();
	}

	private static IRepository getRepository(IStore store) {
		Map<String, String> props = new HashMap<String, String>();
		props.put(IRepository.Props.OVERRIDE_UUID, DEFAULT_REPOSITORY_NAME);
	    props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
	    props.put(IRepository.Props.SUPPORTING_BRANCHES, "false");
	    
	    IRepository repo = CDOServerUtil.createRepository(DEFAULT_REPOSITORY_NAME, store, props);
	    
		return repo;
	}
}
