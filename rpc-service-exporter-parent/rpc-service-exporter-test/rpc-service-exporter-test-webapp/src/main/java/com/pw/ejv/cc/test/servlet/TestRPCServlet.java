package com.pw.ejv.cc.test.servlet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.pw.ejb.cc.commons.rpc.servlet.AbstractRPCServlet;

@WebServlet(urlPatterns = { "/rpc/*" })
@MultipartConfig
public class TestRPCServlet extends AbstractRPCServlet {

	private static final long serialVersionUID = 5610644570034789146L;

	@Override
	protected Object getBussinesObject(HttpServletRequest req, String beanReference) {
		return TestBeanRepository.getInstance().lookup(beanReference);
	}

}
