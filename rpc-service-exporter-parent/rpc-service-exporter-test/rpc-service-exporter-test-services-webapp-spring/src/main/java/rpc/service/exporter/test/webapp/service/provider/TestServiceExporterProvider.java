package rpc.service.exporter.test.webapp.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pw.rpc.service.exporter.commons.factory.ExportedService;
import pw.rpc.service.exporter.commons.factory.ServiceExporterProvider;
import rpc.service.exporter.test.services.impl.HelloWorldBean;

public class TestServiceExporterProvider implements ServiceExporterProvider {

	private static final Map<String, ExportedService> serviceMap;
	static {
		
		ArrayList<String> yachtList = new ArrayList<>();
		yachtList.add("Gunboat");
		yachtList.add("Amel-60ft");
		yachtList.add("Sirius-DS-40ft");
		InnerExportedService expServiceYachList = new InnerExportedService("MyDreamSailBoatList", yachtList);
		
		InnerExportedService expServiceHelloWorld = new InnerExportedService("HelloWorldService", new HelloWorldBean());

		
		serviceMap = new HashMap<>();
		
		serviceMap.put(expServiceYachList.getServiceName(), expServiceYachList);
		serviceMap.put(expServiceHelloWorld.getServiceName(), expServiceHelloWorld);
	}

	@Override
	public List<ExportedService> getExportedServices() {
		return serviceMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public Object getService(String serviceName) {
		return serviceMap.get(serviceName).getService();
	}
	
	
	
	public static class InnerExportedService implements ExportedService {

		private final String serviceName;
		private final Object serviceObject;

		public InnerExportedService(String serviceName, Object serviceObject) {
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

	
	
}
