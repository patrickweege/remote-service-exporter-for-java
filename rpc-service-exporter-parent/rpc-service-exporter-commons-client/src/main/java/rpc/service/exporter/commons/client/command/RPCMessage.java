package rpc.service.exporter.commons.client.command;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import rpc.service.exporter.commons.client.data.Null;
import rpc.service.exporter.commons.client.exception.RPCException;
import rpc.service.exporter.commons.client.exception.RPCRemoteException;
import rpc.service.exporter.commons.client.serialization.SerializationUtil;

public class RPCMessage implements Serializable {

	private static final long serialVersionUID = -3691582614948183507L;
	
	private final String rpcInvokeEndpoint;
	private final String rpcMethodName;
	private final Object[] rpcArguments;
	private final Class<?>[] rpcMethodParameterTypes;

	public RPCMessage(String rpcInvokeEndpoint, Method rpcMethod, Object[] rpcArguments) {
		this.rpcInvokeEndpoint = rpcInvokeEndpoint;
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
	
	public String getRPCInvokeEndpoint() {
		return rpcInvokeEndpoint;
	}
	
	public <T extends Serializable> T executeRPCCall() {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			byte[] serialized = SerializationUtil.serialize(this);

			HttpPost httpPost = new HttpPost(rpcInvokeEndpoint);

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
				
				throw new RPCException();
			} else {
				throw new RuntimeException(response.getStatusLine().getReasonPhrase());	
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
