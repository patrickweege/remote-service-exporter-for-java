package test.rpc.service.exporter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import rpc.service.exporter.commons.client.security.CookieStoreFactory;
import rpc.service.exporter.test.webapp.application.TestApplication;

@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CookieStoreFactoryTest {

	private static final String ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/checkLogin/";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	@Test
	public void testCookieStoreProvider() throws IllegalStateException, IOException {
		
		DefaultTestCookieStoreProvider testCookieStoreProvider = new DefaultTestCookieStoreProvider();
		CookieStore testCookieStore = testCookieStoreProvider.getCookieStore();
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(testCookieStore).build();

		final HttpGet request = new HttpGet(ENDPOINT);

		HttpResponse response = client.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
        
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        
        assertTrue(body.contains("admin"));

	}
	
	@Test
	public void testCookieStoreFactory() throws IllegalStateException, IOException {
		
		DefaultTestCookieStoreProvider testCookieStoreProvider = new DefaultTestCookieStoreProvider();
		CookieStoreFactory.getInstance().setCookieStoreProvider(testCookieStoreProvider);
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(CookieStoreFactory.getInstance().getCookieStore()).build();

		final HttpGet request = new HttpGet(ENDPOINT);

		HttpResponse response = client.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();

        assertEquals(HttpStatus.OK.value(), statusCode);
        
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        
        assertTrue(body.contains("admin"));

	}

	
	
	
}
