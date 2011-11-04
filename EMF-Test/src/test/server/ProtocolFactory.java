package test.server;

import org.eclipse.net4j.signal.SignalProtocol;
import org.eclipse.net4j.signal.SignalReactor;
import org.eclipse.net4j.util.factory.ProductCreationException;
import org.eclipse.spi.net4j.ServerProtocolFactory;

public class ProtocolFactory extends ServerProtocolFactory {
	public ProtocolFactory() {
		super(UploadProtocol.PROTOCOL_NAME);
	}
	
	@Override
	public Object create(String description) throws ProductCreationException {
		return new SignalProtocol<Object>(UploadProtocol.PROTOCOL_NAME) {
			@Override
			protected SignalReactor createSignalReactor(short signalID) {
				switch (signalID) {
					case UploadProtocol.UPLOAD_SIGNAL_ID:
						return super.createSignalReactor(signalID);
					default:
						return super.createSignalReactor(signalID);
				}
				
			}
		};
	}
}
