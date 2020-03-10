package test.rpc.service.exporter.test.lookup;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import pw.rpc.service.exporter.commons.lookup.RPCLookupService;
import rpc.service.exporter.test.services.HelloWorldBeanLocal;
import rpc.service.exporter.test.webapp.application.TestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestRPCServiceExporterIntegration {
	
	private static final String LOOKUP_ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/lookup";
	
	
	@Test
	public void testLookupHelloWorldBean() throws IOException, URISyntaxException {
		HelloWorldBeanLocal helloWorldBean = RPCLookupService.lookup( LOOKUP_ENDPOINT + "/HelloWorldService");

		assertNotNull(helloWorldBean);
	}
	
	
	@Test
	public void testCallSayHallo() throws IOException, URISyntaxException {
		HelloWorldBeanLocal helloWorldBean = RPCLookupService.lookup( LOOKUP_ENDPOINT + "/HelloWorldService");
		
		String sayHello = helloWorldBean.sayHello("Heinz");
		
		assertEquals("Hello 'Heinz'", sayHello);
		
	}

	

}
