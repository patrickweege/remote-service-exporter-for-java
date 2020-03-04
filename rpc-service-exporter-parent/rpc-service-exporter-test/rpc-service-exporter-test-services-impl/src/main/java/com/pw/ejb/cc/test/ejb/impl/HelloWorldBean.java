package com.pw.ejb.cc.test.ejb.impl;

import javax.ejb.Stateless;

import com.pw.ejb.cc.test.ejb.HelloWorldBeanLocal;

/**
 * Session Bean implementation class HelloWorldBean
 */
@Stateless
public class HelloWorldBean implements HelloWorldBeanLocal {

	/**
	 * Default constructor.
	 */
	public HelloWorldBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sayHello(String name) {
		return "Hello '" + name + "'";
	}

}
