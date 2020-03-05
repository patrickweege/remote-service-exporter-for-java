package test.pw.rpc.exporter.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

import pw.rpc.exporter.test.services.HelloWorldBeanLocal;
import pw.rpc.service.exporter.commons.lookup.RPCLookupService;

public class CallServicesTest {
	
	private static final String BASE_LOOKUP_URL = "http://localhost:8080/rpc-service-exporter-test-services-webapp/lookup/"; 
	
	
	@Test
	public void testHelloWorldService() throws IOException, URISyntaxException {
		
		HelloWorldBeanLocal helloWorld = RPCLookupService.lookup(BASE_LOOKUP_URL + "HelloWorldService");
		String sayHello = helloWorld.sayHello("Patrick");
		
		assertEquals("Hello 'Patrick'", sayHello);

	}

	
	@Test
	public void testCallMyDreamSailingBoatService() throws IOException, URISyntaxException {
		
		List<String> boatList = RPCLookupService.lookup(BASE_LOOKUP_URL + "MyDreamSailBoatList");
		String firstBoat = boatList.get(0);
		
		assertEquals("Gunboat", firstBoat);

		int size = boatList.size();
		assertEquals(3, size);

		boatList.add("Beneteau-Oceanis-62ft");
		String lastBoat = boatList.get(size);
		
		assertEquals("Beneteau-Oceanis-62ft", lastBoat);
	}
	
	

}
