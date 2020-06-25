package rse4j.commons.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rse4j.commons.client.command.RPCMessage;
import rse4j.commons.client.command.RemoteCommand;
import rse4j.commons.client.data.Null;
import rse4j.commons.client.exception.RPCRemoteException;
import rse4j.commons.client.proxy.ProxyUtil;
import rse4j.commons.client.serialization.SerializationUtil;
import rse4j.commons.server.factory.ExportedService;
import rse4j.commons.server.factory.ServiceProvider;

@MultipartConfig
public abstract class AbstractRPCServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 3603079835940554586L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRPCServiceServlet.class);
	
	public static final String PARAM_SERVICE_PROVIDER = "serviceProvider";
	public static final String PARAM_LOOKUP_PATH = "lookupPath";
	public static final String PARAM_DESCRIBE_PATH = "describePath";
	public static final String PARAM_INVOKE_PATH = "invokePath";
	public static final String PARAM_CHECK_LOGIN_PATH = "checkLoginPath";
	
	public static final String DEFAULT_LOOKUP_ENDPOINT = "/rse4j/lookup";
	public static final String DEFAULT_DESCRIBE_ENDPOINT = "/rse4j/describe";
	public static final String DEFAULT_INVOKE_ENDPOINT = "/rse4j/invoke";
	public static final String DEFAULT_CHECKLOGIN_ENDPOINT = "/rse4j/check-login";
	
	public static final String DEFAULT_LOOKUP_URL_PATTERN = DEFAULT_LOOKUP_ENDPOINT + "/*";
	public static final String DEFAULT_DESCRIBE_URL_PATTERN = DEFAULT_DESCRIBE_ENDPOINT + "/*";
	public static final String DEFAULT_INVOKE_URL_PATTERN = DEFAULT_INVOKE_ENDPOINT + "/*";
	public static final String DEFAULT_CHECKLOGIN_URL_PATTERN = DEFAULT_CHECKLOGIN_ENDPOINT + "/*";

	
	private static final String PARAM_RPCMessage = "RPCMessage";

	private ServiceProvider serviceProvider;	
	private String lookupPath = DEFAULT_LOOKUP_ENDPOINT;
	private String describePath = DEFAULT_DESCRIBE_ENDPOINT;
	private String invokePath = DEFAULT_INVOKE_ENDPOINT;
	private String checkLoginPath = DEFAULT_CHECKLOGIN_ENDPOINT;

	@Override
	public void init() throws ServletException {
		ServletConfig config = this.getServletConfig();
		this.lookupPath = StringUtils.defaultIfBlank(config.getInitParameter(PARAM_LOOKUP_PATH), DEFAULT_LOOKUP_ENDPOINT);
		this.describePath = StringUtils.defaultIfBlank(config.getInitParameter(PARAM_DESCRIBE_PATH),
				DEFAULT_DESCRIBE_ENDPOINT);
		this.invokePath = StringUtils.defaultIfBlank(config.getInitParameter(PARAM_INVOKE_PATH), DEFAULT_INVOKE_ENDPOINT);
		this.checkLoginPath = StringUtils.defaultIfBlank(config.getInitParameter(PARAM_CHECK_LOGIN_PATH),
				DEFAULT_CHECKLOGIN_ENDPOINT);
		this.serviceProvider = this.initAndGetServiceProvider(config);
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if(LOGGER.isDebugEnabled()) {
				String userName = req.getUserPrincipal() == null ? "NULL" : req.getUserPrincipal().getName();
				StringBuffer requestURL = req.getRequestURL();
				LOGGER.debug("User '{}' Calls: {}", userName, requestURL);
			}
			
			super.service(req, resp);
		} catch(RPCRemoteException e) {
			this.handleRPCException(req, resp, e);
		}
	}
	
	private void handleRPCException(HttpServletRequest req, HttpServletResponse resp, RPCRemoteException rpcException) throws IOException {
		resp.setStatus(RPCRemoteException.STATUS_CODE_RPCEXCEPTION);
		SerializationUtil.serialize(rpcException, resp.getOutputStream());
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isLookupRequest(req)) {
			this.doLookup(req, resp);
		} else if (isDescribeRequest(req)) {
			this.doDescribe(req, resp);
		} else if (isCheckLoginRequest(req)) {
			this.doCheckLogin(req, resp);
		} else {
			throw new ServletException("Service is not enabled");
		}
	}

	protected void doCheckLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Principal userPrincipal = req.getUserPrincipal();
		String userName = (userPrincipal == null ? "" : userPrincipal.getName());
		boolean authenticated = (userName != null);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(StringUtils.LF);
		sb.append("   userName: '").append(userName).append("',").append(StringUtils.LF);
		sb.append("   authenticated: ").append(authenticated).append(StringUtils.LF);
		sb.append("}").append(StringUtils.LF);

		PrintWriter writer = resp.getWriter();
		writer.print(sb.toString());
		
		writer.flush();
		writer.close();
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

	protected void doLookup(HttpServletRequest req, HttpServletResponse resp) {
		OutputStream out = null;
		try {
			out = resp.getOutputStream();

			String serviceName = this.getServiceName(req);
			ExportedService exportedService = getService(serviceName);
			URL invokeBaseURL = this.getInvokeBaseURL(req);

			Object remoteProxy = ProxyUtil.getRemoteProxy(exportedService.getSharedInterface(), new RemoteCommand(invokeBaseURL));
			SerializationUtil.serialize(remoteProxy, out);

		} catch (Exception e) {
			throw new RPCRemoteException(e);
		}
	}

	protected void doInvoke(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			RPCMessage rpcMessage = this.getRPCMessage(req);

			final String serviceName = this.getServiceName(req);
			final Object[] rpcArguments = rpcMessage.getRpcArguments();
			final String rpcMethodName = rpcMessage.getRpcMethodName();
			final Class<?>[] rpcMethodParameterTypes = rpcMessage.getRpcMethodParameterTypes();

			ExportedService exportedService = this.getService(serviceName);

			Object result = MethodUtils.invokeExactMethod(exportedService.getService(), rpcMethodName, rpcArguments,
					rpcMethodParameterTypes);

			if (result == null) {
				result = Null.NULL;	
			}
			
			SerializationUtil.serialize(result, out);
			
		} catch (Exception e) {
			LOGGER.error((String) null, e);
			
			this.sendError(e, req, resp);
		}
	}


	protected ExportedService getService(final String serviceName) {
		return this.getServiceProvider().getService(serviceName);
	}

	protected ServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

	private void sendError(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.reset();
		ServletOutputStream out = resp.getOutputStream();
		resp.setStatus(RPCRemoteException.STATUS_CODE_RPCEXCEPTION);

		SerializationUtil.serialize(new RPCRemoteException(e), out);
	}
	
	protected URL getInvokeBaseURL(HttpServletRequest req) throws MalformedURLException, URISyntaxException {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		String serviceName = this.getServiceName(req);
		
		List<String> segments = URLEncodedUtils.parsePathSegments(contextPath);
		segments.addAll(URLEncodedUtils.parsePathSegments(this.invokePath));
		segments.add(serviceName);
		
		URI uri = new URIBuilder() //
				.setScheme(scheme) //
				.setHost(serverName) //
				.setPort(serverPort) //
				.setPath(contextPath) //
				.setPathSegments(segments) //
				.build();
		
		return uri.toURL();
	}

	protected String getServiceName(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		String requestURL = req.getRequestURL().toString();

		String serviceName = StringUtils.substringAfterLast(requestURL, servletPath);
		serviceName = StringUtils.removeStart(serviceName, "/");
		serviceName = StringUtils.substringBefore(serviceName, "/");

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

	private boolean isCheckLoginRequest(HttpServletRequest req) {
		return isServletPathEquals(req, checkLoginPath);
	}

	
	private boolean isServletPathEquals(HttpServletRequest req, String pathToCheck) {
		String servletPath = req.getServletPath();
		return StringUtils.equals(servletPath, pathToCheck);
	}

	protected ServiceProvider initAndGetServiceProvider(ServletConfig config) throws ServletException {
		try {
			String factoryBeanClassName = config.getInitParameter(PARAM_SERVICE_PROVIDER);
			Class<?> factoryBeanClass = Class.forName(factoryBeanClassName);
			return (ServiceProvider) factoryBeanClass.newInstance();
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

