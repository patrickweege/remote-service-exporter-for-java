package rpc.service.exporter.test.webapp.servlet.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/checkLogin/*" }) //
public class CheckLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 235989939492741001L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.getWriter().write("user:" + req.getUserPrincipal().getName());
		
		resp.flushBuffer();
		
	}
	
}
