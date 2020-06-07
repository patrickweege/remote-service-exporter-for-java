package rpc.service.exporter.commons.factory;

public class SimpleExportedService implements ExportedService {

	private final String serviceName;
	private final Object serviceObject;

	public SimpleExportedService(String serviceName, Object serviceObject) {
			this.serviceName = serviceName;
			this.serviceObject = serviceObject;
		}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public Object getService() {
		return serviceObject;
	}

}
