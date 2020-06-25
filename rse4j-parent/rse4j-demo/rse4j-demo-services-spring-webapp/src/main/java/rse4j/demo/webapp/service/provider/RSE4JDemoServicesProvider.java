package rse4j.demo.webapp.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import rse4j.commons.server.factory.BasicExportedService;
import rse4j.commons.server.factory.ExportedService;
import rse4j.commons.server.factory.ServiceProvider;
import rse4j.demo.services.dreamsailboat.DreamSailboatService;
import rse4j.demo.services.dreamsailboat.impl.DreamSailboatServiceImpl;
import rse4j.demo.services.hello.HelloWorldBeanLocal;
import rse4j.demo.services.hello.impl.HelloWorldBean;
import rse4j.demo.services.tree.TreeService;
import rse4j.demo.services.tree.impl.TreeServiceImpl;

public class RSE4JDemoServicesProvider implements ServiceProvider {

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
