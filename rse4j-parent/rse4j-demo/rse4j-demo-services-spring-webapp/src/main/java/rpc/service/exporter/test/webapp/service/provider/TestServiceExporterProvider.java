package rpc.service.exporter.test.webapp.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import rpc.service.exporter.commons.factory.BasicExportedService;
import rpc.service.exporter.commons.factory.ExportedService;
import rpc.service.exporter.commons.factory.ServiceExporterProvider;
import rpc.service.exporter.test.services.dreamsailboat.DreamSailboatService;
import rpc.service.exporter.test.services.dreamsailboat.impl.DreamSailboatServiceImpl;
import rpc.service.exporter.test.services.hello.HelloWorldBeanLocal;
import rpc.service.exporter.test.services.hello.impl.HelloWorldBean;
import rpc.service.exporter.test.services.tree.TreeService;
import rpc.service.exporter.test.services.tree.impl.TreeServiceImpl;

public class TestServiceExporterProvider implements ServiceExporterProvider {

	private static final Map<String, ExportedService> serviceMap;
	static {

		BasicExportedService expServiceTree = new BasicExportedService("TreeService", TreeService.class, new TreeServiceImpl());

		BasicExportedService expServiceYachList = new BasicExportedService("MyDreamSailBoatList", DreamSailboatService.class, new DreamSailboatServiceImpl());
		
		BasicExportedService expServiceHelloWorld = new BasicExportedService("HelloWorldService", HelloWorldBeanLocal.class, new HelloWorldBean());

		List<String> simpleList = new ArrayList<>();
		simpleList.add("Element-1");
		simpleList.add("Element-2");
		BasicExportedService expServiceSimpleList = new BasicExportedService("SimpleArrayList", List.class, simpleList);
		
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
	public ExportedService getService(String serviceName) {
		return serviceMap.get(serviceName);
	}
	
	
}
