package rpc.service.exporter.test.services.impl;

import javax.ejb.Stateless;

import rpc.service.exporter.test.services.HelloWorldBeanLocal;

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
