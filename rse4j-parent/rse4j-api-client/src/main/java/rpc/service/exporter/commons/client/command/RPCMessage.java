package rpc.service.exporter.commons.client.command;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rpc.service.exporter.commons.client.data.Null;
import rpc.service.exporter.commons.client.exception.RPCException;
import rpc.service.exporter.commons.client.exception.RPCRemoteException;
import rpc.service.exporter.commons.client.security.CookieStoreFactory;
import rpc.service.exporter.commons.client.serialization.SerializationUtil;

public class RPCMessage implements Serializable {

	private static final long serialVersionUID = -3691582614948183507L;
	
	Logger LOGGER = LoggerFactory.getLogger(RPCMessage.class);
	
	private final URL rpcInvokeBaseURL;
	private final String rpcMethodName;
	private final Object[] rpcArguments;
	private final Class<?>[] rpcMethodParameterTypes;
	
	public RPCMessage(URL rpcInvokeBaseURL, Method rpcMethod, Object[] rpcArguments) {
		this.rpcInvokeBaseURL = rpcInvokeBaseURL;
		this.rpcMethodName = rpcMethod.getName();
		this.rpcMethodParameterTypes = rpcMethod.getParameterTypes();
		this.rpcArguments = rpcArguments;
	}

	public String getRpcMethodName() {
		return rpcMethodName;
	}
	
	public Class<?>[] getRpcMethodParameterTypes() {
		return rpcMethodParameterTypes;
	}
	
	public Object[] getRpcArguments() {
		return rpcArguments;
	}
	
	public URL getInvokeBaseURL() {
		return rpcInvokeBaseURL;
	}
	
	public <T extends Serializable> T executeRPCCall() {
		final CookieStore cookieStore = CookieStoreFactory.getInstance().getCookieStore();
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build()) {
			byte[] serialized = SerializationUtil.serialize(this);

			URI invokeMethodURI = this.getInvokeMethod();
			
			HttpPost httpPost = new HttpPost(invokeMethodURI);
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("RPCMessage", serialized);

			HttpEntity multipart = builder.build();
			httpPost.setEntity(multipart);
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				byte[] bytes = EntityUtils.toByteArray(entity);

				T retValue = SerializationUtil.deserialize(bytes);

				if(Null.NULL.equals(retValue)) {
					retValue = null;
				}
				return retValue;
			} else if(response.getStatusLine().getStatusCode() == RPCRemoteException.STATUS_CODE_RPCEXCEPTION) {
				HttpEntity entity = response.getEntity();
				byte[] bytes = EntityUtils.toByteArray(entity);
				
				RPCRemoteException e = SerializationUtil.deserialize(bytes);
				
				throw new RPCException(e);
			} else {
				throw new RuntimeException(response.getStatusLine().getReasonPhrase());	
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private URI getInvokeMethod() throws MalformedURLException, URISyntaxException {
		URIBuilder builder = new URIBuilder(this.rpcInvokeBaseURL.toURI());
		List<String> segments = builder.getPathSegments();
		segments.add(this.rpcMethodName);
		builder.setPathSegments(segments);
		return builder.build();
	}
	
	
	public static void main(String[] args) {
		List<String> splitPathSegments = URLEncodedUtils.parsePathSegments("/platin/teste");
		
		System.out.println(splitPathSegments);
	}
	

}
