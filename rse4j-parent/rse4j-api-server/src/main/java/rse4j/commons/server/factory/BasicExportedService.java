package rse4j.commons.server.factory;

public class BasicExportedService implements ExportedService {

	private final String serviceName;
	private final Object serviceInstance;
	private final Class<?> sharedInterface;

	public <T> BasicExportedService(String serviceName, Class<T> sharedInterface, T serviceInstance) {
			this.serviceName = serviceName;
			this.serviceInstance = serviceInstance;
			this.sharedInterface = sharedInterface;
		}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public Object getService() {
		return serviceInstance;
	}

	@Override
	public Class<?> getSharedInterface() {
		return sharedInterface;
	}

}
