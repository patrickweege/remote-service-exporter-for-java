package rpc.service.exporter.commons.client.security;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FormBasedAuthenticationCookieStoreProvider implements ICookieStoreProvider {

	private final String userName;
	private final String password;
	private final String protectedURL;
	private final String jSecurityCheckURL;
	
	private CookieStore cookieStore = null;

	public FormBasedAuthenticationCookieStoreProvider(String userName, String password, String protectedURL,
			String jSecurityCheckURL) {
		super();
		this.userName = userName;
		this.password = password;
		this.protectedURL = protectedURL;
		this.jSecurityCheckURL = jSecurityCheckURL;
	}

	@Override
	public CookieStore getCookieStore() {
		if(this.cookieStore == null) {
			this.authenticate();
		}
		return this.cookieStore;
	}

	private void authenticate() {
		this.cookieStore = new BasicCookieStore();
		try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build()) {
			HttpGet httpget = new HttpGet(this.protectedURL);
			
			try(CloseableHttpResponse response1 = httpclient.execute(httpget)) {
				HttpEntity entity = response1.getEntity();

				System.out.println("Login Status Line : " + response1.getStatusLine());
				System.out.println("Login form content: " + EntityUtils.toString(entity));

				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
			}

			HttpUriRequest login = RequestBuilder.post()
					.setUri(this.jSecurityCheckURL)
						.addParameter("j_username", this.userName)
						.addParameter("j_password", this.password)
						.build();

			try (CloseableHttpResponse response2 = httpclient.execute(login)) {
				HttpEntity entity = response2.getEntity();

				System.out.println("Login form get: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post logon cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
