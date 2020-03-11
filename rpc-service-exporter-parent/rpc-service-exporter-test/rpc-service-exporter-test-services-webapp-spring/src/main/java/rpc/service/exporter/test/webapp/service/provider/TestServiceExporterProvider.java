package rpc.service.exporter.test.webapp.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import rpc.service.exporter.commons.factory.ExportedService;
import rpc.service.exporter.commons.factory.ServiceExporterProvider;
import rpc.service.exporter.test.services.dreamsailboat.impl.DreamSailboatServiceImpl;
import rpc.service.exporter.test.services.hello.impl.HelloWorldBean;
import rpc.service.exporter.test.services.tree.impl.TreeServiceImpl;

public class TestServiceExporterProvider implements ServiceExporterProvider {

	private static final Map<String, ExportedService> serviceMap;
	static {

		InnerExportedService expServiceTree = new InnerExportedService("TreeService", new TreeServiceImpl());

		InnerExportedService expServiceYachList = new InnerExportedService("MyDreamSailBoatList", new DreamSailboatServiceImpl());
		
		InnerExportedService expServiceHelloWorld = new InnerExportedService("HelloWorldService", new HelloWorldBean());

		List<String> simpleList = new ArrayList<>();
		simpleList.add("Element-1");
		simpleList.add("Element-2");
		InnerExportedService expServiceSimpleList = new InnerExportedService("SimpleArrayList", simpleList);
		
		serviceMap = new HashMap<>();
		
		serviceMap.put(expServiceTree.getServiceName(), expServiceTree);
		serviceMap.put(expServiceYachList.getServiceName(), expServiceYachList);
		serviceMap.put(expServiceHelloWorld.getServiceName(), expServiceHelloWorld);
		serviceMap.put(expServiceSimpleList.getServiceName(), expServiceSimpleList);
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
