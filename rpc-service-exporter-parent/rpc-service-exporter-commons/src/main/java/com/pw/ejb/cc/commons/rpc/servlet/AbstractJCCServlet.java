package com.pw.ejb.cc.commons.rpc.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.pw.ejb.cc.commons.serialization.SerializationUtil;

public abstract class AbstractJCCServlet extends HttpServlet {

	private static final long serialVersionUID = 6816474609158132420L;
	
	private static final String PARAM_BEAN = "bean";

	protected String getParamBean(HttpServletRequest req) {
		return (String) this.getFromRequest(req, PARAM_BEAN);
	}

	protected Object getFromRequest(HttpServletRequest req, String parameter) {
		if("GET".equals(req.getMethod())) {
			return req.getParameter(parameter);
		} else {
			return (String) req.getAttribute(parameter);
		}
	}
	
	protected <T extends Serializable> T getMultipartObject(HttpServletRequest req, final String partName) throws ServletException, IOException  {
		Part part = req.getPart(partName);
		try (InputStream in = part.getInputStream()) {
			byte[] byteArray = IOUtils.toByteArray(in);
			
			T multipartObject = SerializationUtil.deserialize(byteArray);
			
			return multipartObject;
			
		}
	}

	
	protected abstract Object getBussinesObject(HttpServletRequest req, String beanReference);
	
}
