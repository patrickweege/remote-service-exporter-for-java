package com.pw.ejb.cc.commons.lookup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.pw.ejb.cc.commons.serialization.SerializationUtil;

public class CCLookup {

	public static <T extends Object> T lookup(String lookupEndpoint, String beanName)
			throws IOException, URISyntaxException {

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			URI uri = new URIBuilder(lookupEndpoint).setParameter("bean", beanName).build();
			HttpGet httpget = new HttpGet(uri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(httpget);

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

	public static void main(String[] args) throws IOException, URISyntaxException {
		List l = CCLookup.lookup("http://localhost:8080/pw-ejb-cc-test-war/lookup", "ejb/meisterlist");
		
		Object element = l.get(1);
		
		System.out.println(element);
		
	}

}
