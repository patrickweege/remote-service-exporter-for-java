package pw.rpc.service.exporter.commons.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import pw.rpc.service.exporter.commons.command.RPCMessage;
import pw.rpc.service.exporter.commons.command.RemoteCommand;
import pw.rpc.service.exporter.commons.factory.ServiceExporterProvider;
import pw.rpc.service.exporter.commons.proxy.ProxyUtil;
import pw.rpc.service.exporter.commons.serialization.SerializationUtil;

@MultipartConfig
public abstract class AbstractRPCServiceServlet extends HttpServlet {

	public static final String INIT_RPC_SERVICE_EXPORTER_PROVIDER = "rpcServiceExporterProvider";
	public static final String INIT_LOOKUP_PATH = "lookupPath";
	public static final String INIT_DESCRIBE_PATH = "describePath";
	public static final String INIT_INVOKE_PATH = "invokePath";

	private static final String PARAM_RPCMessage = "RPCMessage";

	private ServiceExporterProvider rpcServiceExporterBeanProvider;
	private String lookupPath = "lookup";
	private String describePath = "describe";
	private String invokePath = "invoke";

	@Override
	public void init() throws ServletException {
		ServletConfig config = this.getServletConfig();
		this.lookupPath = config.getInitParameter(INIT_LOOKUP_PATH);
		this.describePath = config.getInitParameter(INIT_DESCRIBE_PATH);
		this.invokePath = config.getInitParameter(INIT_INVOKE_PATH);
		this.rpcServiceExporterBeanProvider = this.getRPCServiceFactoryBean(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isLookupRequest(req)) {
			this.doLookup(req, resp);
		} else if (isLookupRequest(req)) {
			this.doDescribe(req, resp);
		} else {
			throw new ServletException("Service is not enabled");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isInvokeRequest(req)) {
			doInvoke(req, resp);
		} else {
			throw new ServletException("Service is not enabled");
		}
	}

	protected void doDescribe(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		throw new ServletException("doDescribe() ist not implemented now");
	}

	protected void doLookup(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try (OutputStream out = resp.getOutputStream()) {

			String serviceName = this.getServiceName(req);
			Object serviceBean = this.rpcServiceExporterBeanProvider.getService(serviceName);
			String invokeEndpoint = this.getInvokeEndpoint(req);

			Object remoteProxy = ProxyUtil.getRemoteProxy(serviceBean, new RemoteCommand(invokeEndpoint));
			SerializationUtil.serialize(remoteProxy, out);

			// resp.getOutputStream().write(serialized);

			out.flush();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doInvoke(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try (OutputStream out = resp.getOutputStream()) {
			RPCMessage rpcMessage = this.getRPCMessage(req);

			final String serviceName = this.getServiceName(req);
			final Object[] rpcArguments = rpcMessage.getRpcArguments();
			final String rpcMethodName = rpcMessage.getRpcMethodName();
			final Class<?>[] rpcMethodParameterTypes = rpcMessage.getRpcMethodParameterTypes();

			Object serviceBean = this.rpcServiceExporterBeanProvider.getService(serviceName);

			Object result = MethodUtils.invokeExactMethod(serviceBean, rpcMethodName, rpcArguments,
					rpcMethodParameterTypes);

			if (result != null) {
				SerializationUtil.serialize(result, out);
				// resp.getOutputStream().write(serialize);
			}
			resp.setStatus(HttpServletResponse.SC_OK);

			out.flush();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected String getInvokeEndpoint(HttpServletRequest req) {
		String requestURL = req.getRequestURL().toString();
		String servletPath = req.getServletPath();
		String serviceName = this.getServiceName(req);

		StringBuilder endpoint = new StringBuilder();
		endpoint.append(StringUtils.substringBefore(requestURL, servletPath));
		endpoint.append("/");
		endpoint.append(this.invokePath);
		endpoint.append("/");
		endpoint.append(serviceName);

		return endpoint.toString();

	}

	protected String getServiceName(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		String requestURL = req.getRequestURL().toString();

		String serviceName = StringUtils.substringAfterLast(requestURL, servletPath);
		serviceName = StringUtils.removeStart(serviceName, "/");

		return serviceName;
	}

	protected boolean isLookupRequest(HttpServletRequest req) {
		return isServletPathEquals(req, lookupPath);
	}

	protected boolean isDescribeRequest(HttpServletRequest req) {
		return isServletPathEquals(req, describePath);
	}

	protected boolean isInvokeRequest(HttpServletRequest req) {
		return isServletPathEquals(req, invokePath);
	}

	private boolean isServletPathEquals(HttpServletRequest req, String pathToCheck) {
		String servletPath = req.getServletPath();
		return StringUtils.equals(servletPath, pathToCheck);
	}

	private final ServiceExporterProvider getRPCServiceFactoryBean(ServletConfig config) throws ServletException {
		try {
			String factoryBeanClassName = config.getInitParameter(INIT_RPC_SERVICE_EXPORTER_PROVIDER);
			Class<?> factoryBeanClass = Class.forName(factoryBeanClassName);
			return (ServiceExporterProvider) factoryBeanClass.newInstance();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected RPCMessage getRPCMessage(HttpServletRequest req) throws ServletException, IOException {
		return this.getMultipartObject(req, PARAM_RPCMessage);
	}

	private <T extends Serializable> T getMultipartObject(HttpServletRequest req, final String partName)
			throws ServletException, IOException {
		Part part = req.getPart(partName);
		try (InputStream in = part.getInputStream()) {
			byte[] byteArray = IOUtils.toByteArray(in);

			T multipartObject = SerializationUtil.deserialize(byteArray);

			return multipartObject;

		}
	}

}
