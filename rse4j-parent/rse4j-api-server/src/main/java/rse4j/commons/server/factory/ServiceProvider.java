package rse4j.commons.server.factory;

import java.util.List;

public interface ServiceProvider {
	
	public List<ExportedService> getExportedServices();
	
	public ExportedService getService(String serviceName);
	
}
