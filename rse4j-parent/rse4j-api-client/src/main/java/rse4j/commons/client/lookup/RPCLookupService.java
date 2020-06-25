package rse4j.commons.client.lookup;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import rse4j.commons.client.security.CookieStoreFactory;
import rse4j.commons.client.serialization.SerializationUtil;

public class RPCLookupService {

	public static <T extends Object> T lookup(String lookupEndpoint) throws IOException, URISyntaxException {
		
		final CookieStore cookieStore = CookieStoreFactory.getInstance().getCookieStore();
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build()) {

			HttpGet httpget = new HttpGet(lookupEndpoint);
			
			HttpResponse response = client.execute(httpget);
			
			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}

			// Now pull back the response object
			HttpEntity httpEntity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(httpEntity);

			T proxy = SerializationUtil.deserialize(bytes);

			return proxy;
		}
		
		
	}
}
