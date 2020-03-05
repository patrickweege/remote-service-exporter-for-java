package pw.rpc.service.exporter.commons.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.reflect.MethodUtils;

import pw.rpc.service.exporter.commons.command.RPCMessage;
import pw.rpc.service.exporter.commons.serialization.SerializationUtil;

public abstract class _AbstractRPCServiceExporterServlet extends AbstractJCCServlet {

	private static final long serialVersionUID = -3659253319536527686L;

	private static final String PARAM_RPCMessage = "RPCMessage";

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		try (OutputStream out = resp.getOutputStream()) {
//
//			RPCMessage rpcMessage = this.getRPCMessage(req);
//			final String rpcBeanReference = rpcMessage.getRpcBeanReference();
//			final Object[] rpcArguments = rpcMessage.getRpcArguments();
//			final String rpcMethodName = rpcMessage.getRpcMethodName();
//			final Class<?>[] rpcMethodParameterTypes = rpcMessage.getRpcMethodParameterTypes();
//
//			Object bussinesObject = this.getBussinesObject(req, rpcBeanReference);
//
//			Object result = MethodUtils.invokeExactMethod(bussinesObject, rpcMethodName, rpcArguments, rpcMethodParameterTypes);
//
//			if (result != null) {
//				byte[] serialize = SerializationUtil.serialize(result);
//				resp.getOutputStream().write(serialize);
//			}
//			out.flush();
//			resp.setStatus(HttpServletResponse.SC_OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//		}
//
//	}

	protected RPCMessage getRPCMessage(HttpServletRequest req) throws ServletException, IOException {
		return super.getMultipartObject(req, PARAM_RPCMessage);
	}

}
