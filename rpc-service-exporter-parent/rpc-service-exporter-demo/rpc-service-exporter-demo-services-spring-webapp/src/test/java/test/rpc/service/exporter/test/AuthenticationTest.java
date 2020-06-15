package test.rpc.service.exporter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import rpc.service.exporter.test.webapp.application.TestApplication;

@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class AuthenticationTest {

	private static final String ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/checkLogin/";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		addAuthenticationCookie(cookieStore);
		
		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

		final HttpGet request = new HttpGet(ENDPOINT);

		HttpResponse response = client.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();

		System.out.println(">>>>>> " + statusCode);

	}
	
	@Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException {
		
        TestRestTemplate restTemplate = new TestRestTemplate("admin", "admin");
        URL base = new URL(ENDPOINT);

        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
        
        HttpHeaders headers = response.getHeaders();
        System.out.println(headers);
  
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("admin"));
    }
	
	
	private static void addAuthenticationCookie(BasicCookieStore cookieStore) throws MalformedURLException {
        TestRestTemplate restTemplate = new TestRestTemplate(USERNAME, PASSWORD);
        URL base = new URL(ENDPOINT);

        ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);
        
        HttpHeaders headers = response.getHeaders();

        String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
        List<HttpCookie> cookies = HttpCookie.parse(setCookie);
        for (HttpCookie httpCookie : cookies) {
    		BasicClientCookie cookie = new BasicClientCookie(httpCookie.getName(), httpCookie.getValue());
    		cookie.setDomain(httpCookie.getDomain());
    		cookie.setPath(httpCookie.getPath());
			
    		cookieStore.addCookie(cookie);
		}
	}
	
	
	
}
