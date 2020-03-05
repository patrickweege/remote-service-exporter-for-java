package pw.rpc.service.exporter.commons.factory;

import java.util.List;

public interface ServiceExporterProvider {
	
	public List<ExportedService> getExportedServices();
	
	public Object getService(String serviceName);
	
}
