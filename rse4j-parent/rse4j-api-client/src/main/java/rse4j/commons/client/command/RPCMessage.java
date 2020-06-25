package rse4j.commons.client.command;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rse4j.commons.client.data.Null;
import rse4j.commons.client.exception.RPCException;
import rse4j.commons.client.exception.RPCRemoteException;
import rse4j.commons.client.security.CookieStoreFactory;
import rse4j.commons.client.serialization.SerializationUtil;

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
		LOGGER.debug("[BEGIN] - executeRPCCall()");
		final CookieStore cookieStore = CookieStoreFactory.getInstance().getCookieStore();
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build()) {
			
			LOGGER.debug("[START] Serialization of Arguments");
			byte[] serialized = SerializationUtil.serialize(this);
			LOGGER.debug("[END] Serialization of Arguments");
			
			HttpPost httpPost = new HttpPost(rpcInvokeBaseURL.toURI());
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("RPCMessage", serialized);

			HttpEntity multipart = builder.build();
			httpPost.setEntity(multipart);
			
			LOGGER.debug("[START] Execute Post to {} ", httpPost.getURI());
			CloseableHttpResponse response = client.execute(httpPost);
			LOGGER.debug("[END] Execute Post");


			final int statusCode = response.getStatusLine().getStatusCode();
			LOGGER.debug("executeRPCCall - Startus Code was {}", statusCode);
			
			if(statusCode == 200) {
				HttpEntity entity = response.getEntity();

				byte[] bytes = EntityUtils.toByteArray(entity);

				LOGGER.debug("[START] Deserialization of Result");
				T retValue = SerializationUtil.deserialize(bytes);
				LOGGER.debug("[END] Deserialization of Result");
				
				if(Null.NULL.equals(retValue)) {
					retValue = null;
				}
				return retValue;
			} else if(statusCode == RPCRemoteException.STATUS_CODE_RPCEXCEPTION) {
				HttpEntity entity = response.getEntity();
				byte[] bytes = EntityUtils.toByteArray(entity);
				
				RPCRemoteException e = SerializationUtil.deserialize(bytes);
				
				throw new RPCException(e);
			} else {
				throw new RuntimeException(response.getStatusLine().getReasonPhrase());	
			}
		} catch (IOException | URISyntaxException e) {
			LOGGER.error("", e);
			throw new RuntimeException(e);
		} finally {
			LOGGER.debug("[END] - executeRPCCall()");
		}
	}
}
