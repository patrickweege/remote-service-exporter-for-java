package rpc.service.exporter.commons.factory;

import java.util.List;

public interface ServiceExporterProvider {
	
	public List<ExportedService> getExportedServices();
	
	public ExportedService getService(String serviceName);
	
}
