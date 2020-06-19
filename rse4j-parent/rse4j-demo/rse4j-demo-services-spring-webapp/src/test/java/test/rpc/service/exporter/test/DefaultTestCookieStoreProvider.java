package test.rpc.service.exporter.test;

import java.net.HttpCookie;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import rpc.service.exporter.commons.client.security.ICookieStoreProvider;

public class DefaultTestCookieStoreProvider implements ICookieStoreProvider {

	private static final String CHECK_LOGIN_ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/checkLogin/";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	private String userName;
	private String password;
	private String checkLoginEndpoint;

	public DefaultTestCookieStoreProvider() {
		this(USERNAME, PASSWORD, CHECK_LOGIN_ENDPOINT);
	}
	
	public DefaultTestCookieStoreProvider(String userName, String password, String checkLoginEndpoint) {
		this.userName = userName;
		this.password = password;
		this.checkLoginEndpoint = checkLoginEndpoint;
	}
	
	@Override
	public CookieStore getCookieStore() {
		BasicCookieStore cookieStore = new BasicCookieStore();
        TestRestTemplate restTemplate = new TestRestTemplate(userName, password);

        ResponseEntity<String> response = restTemplate.getForEntity(checkLoginEndpoint, String.class);
        
        HttpHeaders headers = response.getHeaders();

        String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
        List<HttpCookie> cookies = HttpCookie.parse(setCookie);
        for (HttpCookie httpCookie : cookies) {
    		BasicClientCookie cookie = new BasicClientCookie(httpCookie.getName(), httpCookie.getValue());
			cookie.setDomain(StringUtils.defaultString(httpCookie.getDomain(), "localhost"));
			cookie.setPath(httpCookie.getPath());
			
    		cookieStore.addCookie(cookie);
		}
        //BasicClientCookie cookie = new BasicClientCookie(headers.getFirst("X-CSRF-PARAM"), headers.get("X-CSRF-TOKEN"));
     
        return cookieStore;
	}
	

}
