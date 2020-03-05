package pw.rpc.service.exporter.test.servlet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import pw.rpc.service.exporter.commons.servlet.AbstractRPCServiceServlet;

@WebServlet(urlPatterns = { "/describe/*", "/lookup/*", "/invoke/*" }, //
		initParams = { //
				@WebInitParam( //
						name = AbstractRPCServiceServlet.INIT_RPC_SERVICE_EXPORTER_PROVIDER, //
						value = "pw.rpc.exporter.test.bean.services.provider.TestServiceExporterProvider"), //
				@WebInitParam(name = AbstractRPCServiceServlet.INIT_LOOKUP_PATH, value = "/lookup"), //
				@WebInitParam(name = AbstractRPCServiceServlet.INIT_DESCRIBE_PATH, value = "/describe"), //
				@WebInitParam(name = AbstractRPCServiceServlet.INIT_INVOKE_PATH, value = "/invoke") //
		}//
)
@MultipartConfig
public class TestRPCServiceServlet extends AbstractRPCServiceServlet {

	private static final long serialVersionUID = 5610644570034789146L;

}
