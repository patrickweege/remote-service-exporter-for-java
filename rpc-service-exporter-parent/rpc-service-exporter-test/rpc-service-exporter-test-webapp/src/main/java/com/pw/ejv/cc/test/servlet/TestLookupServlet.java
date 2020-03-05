package com.pw.ejv.cc.test.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import pw.rpc.service.exporter.commons.servlet.AbstractLookupServlet;

@WebServlet(urlPatterns = { "/lookup/*" })
public class TestLookupServlet extends AbstractLookupServlet {

	private static final long serialVersionUID = 5610644570034789146L;

	@Override
	protected String getCommandRPCServletPath(HttpServletRequest req) {
		return "http://localhost:8080/pw-ejb-cc-test-war/rpc/";
	}

	@Override
	protected Object getBussinesObject(HttpServletRequest req, String beanReference) {
		return TestBeanRepository.getInstance().lookup(beanReference);
	}

}
