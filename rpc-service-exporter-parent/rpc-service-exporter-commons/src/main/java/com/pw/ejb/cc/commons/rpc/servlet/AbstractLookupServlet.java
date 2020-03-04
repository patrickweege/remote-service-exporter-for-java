package com.pw.ejb.cc.commons.rpc.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pw.ejb.cc.commons.command.remote.RemoteCommand;
import com.pw.ejb.cc.commons.proxy.ProxyUtil;
import com.pw.ejb.cc.commons.serialization.SerializationUtil;

public abstract class AbstractLookupServlet extends AbstractJCCServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (OutputStream out = resp.getOutputStream()) {

			String rpcBeanReference = getParamBean(req);
			
			Object bussinesObject = this.getBussinesObject(req, rpcBeanReference);
			String paramBean = this.getParamBean(req);
			
			String path = getCommandRPCServletPath(req);
			
			Object remoteProxy = ProxyUtil.getRemoteProxy(bussinesObject, new RemoteCommand(path, paramBean));
			byte[] serialized = SerializationUtil.serialize(remoteProxy);
			
			resp.getOutputStream().write(serialized);
			
			out.flush();
		}
	}
	
	protected abstract String getCommandRPCServletPath(HttpServletRequest req);

}
