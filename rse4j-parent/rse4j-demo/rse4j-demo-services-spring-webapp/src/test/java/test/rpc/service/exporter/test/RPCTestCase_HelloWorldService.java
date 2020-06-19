package test.rpc.service.exporter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rpc.service.exporter.commons.client.lookup.RPCLookupService;
import rpc.service.exporter.commons.client.security.CookieStoreFactory;
import rpc.service.exporter.test.services.hello.HelloWorldBeanLocal;
import rpc.service.exporter.test.webapp.application.TestApplication;

@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RPCTestCase_HelloWorldService {
	
	private static final String CKECK_LOGIN_ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/checkLogin/";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	
	private static final String LOOKUP_ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/lookup";
	
	@BeforeEach
	public void beforeEach() {
		DefaultTestCookieStoreProvider testCookieStoreProvider = new DefaultTestCookieStoreProvider();
		CookieStoreFactory.getInstance().setCookieStoreProvider(testCookieStoreProvider);
	}
	
	
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
