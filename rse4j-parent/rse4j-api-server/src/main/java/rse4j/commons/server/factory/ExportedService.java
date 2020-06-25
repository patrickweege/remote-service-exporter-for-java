package rse4j.commons.server.factory;

public interface ExportedService {

	public String getServiceName();

	public Object getService();
	
	public Class<?> getSharedInterface(); 
	
	
}
