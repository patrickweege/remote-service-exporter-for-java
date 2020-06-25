package test.rse4j.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rse4j.commons.client.lookup.RPCLookupService;
import rse4j.commons.client.security.CookieStoreFactory;
import rse4j.demo.services.hello.HelloWorldBeanLocal;
import rse4j.demo.webapp.application.TestApplication;

@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RSE4JTestCase_HelloWorldService {
	
	private static final String LOOKUP_ENDPOINT = TestConfiguration.TEST_LOOKUP_PATH +  "/HelloWorldService";
	
	@BeforeEach
	public void beforeEach() {
		RSE4JTestCookieStoreProvider testCookieStoreProvider = new RSE4JTestCookieStoreProvider();
		CookieStoreFactory.getInstance().setCookieStoreProvider(testCookieStoreProvider);
	}
	
	
	@Test
	public void testLookupHelloWorldBean() throws IOException, URISyntaxException {
		HelloWorldBeanLocal helloWorldBean = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		assertNotNull(helloWorldBean);
	}
	
	
	@Test
	public void testCallSayHallo() throws IOException, URISyntaxException {
		HelloWorldBeanLocal helloWorldBean = RPCLookupService.lookup( LOOKUP_ENDPOINT);
		
		String sayHello = helloWorldBean.sayHello("Heinz");
		
		assertEquals("Hello 'Heinz'", sayHello);
		
	}

	

}
