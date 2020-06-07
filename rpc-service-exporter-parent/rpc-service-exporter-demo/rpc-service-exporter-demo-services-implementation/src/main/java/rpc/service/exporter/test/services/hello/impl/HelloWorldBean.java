package rpc.service.exporter.test.services.hello.impl;

import javax.ejb.Stateless;

import rpc.service.exporter.test.services.hello.HelloWorldBeanLocal;

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
