package rse4j.demo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rse4j.commons.server.servlet.AbstractRPCServiceServlet;

@WebServlet( //
		urlPatterns = { //
				AbstractRPCServiceServlet.DEFAULT_CHECKLOGIN_URL_PATTERN, //
				AbstractRPCServiceServlet.DEFAULT_DESCRIBE_URL_PATTERN, //
				AbstractRPCServiceServlet.DEFAULT_LOOKUP_URL_PATTERN, //
				AbstractRPCServiceServlet.DEFAULT_INVOKE_URL_PATTERN //
		}, //
		initParams = { //
				@WebInitParam( //
						name = AbstractRPCServiceServlet.PARAM_SERVICE_PROVIDER, //
						value = "rse4j.demo.webapp.service.provider.RSE4JDemoServicesProvider"), //
				@WebInitParam(name = AbstractRPCServiceServlet.PARAM_LOOKUP_PATH, value = AbstractRPCServiceServlet.DEFAULT_LOOKUP_ENDPOINT), //
				@WebInitParam(name = AbstractRPCServiceServlet.PARAM_DESCRIBE_PATH, value = AbstractRPCServiceServlet.DEFAULT_DESCRIBE_ENDPOINT), //
				@WebInitParam(name = AbstractRPCServiceServlet.PARAM_INVOKE_PATH, value = AbstractRPCServiceServlet.DEFAULT_INVOKE_ENDPOINT), //
				@WebInitParam(name = AbstractRPCServiceServlet.PARAM_CHECK_LOGIN_PATH, value = AbstractRPCServiceServlet.DEFAULT_CHECKLOGIN_ENDPOINT) //
		}//
)
@MultipartConfig
public class RSE4JDemoServlet extends AbstractRPCServiceServlet {

	private static final long serialVersionUID = 5610644570034789146L;

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.service(req, resp);
	}
}
