package rpc.service.exporter.commons.client.lookup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import rpc.service.exporter.commons.client.serialization.SerializationUtil;

public class RPCLookupService {

	
	public static <T extends Object> T lookup(String lookupEndpoint)
			throws IOException, URISyntaxException {

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			URI uri = new URIBuilder(lookupEndpoint).build();
			
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
}
