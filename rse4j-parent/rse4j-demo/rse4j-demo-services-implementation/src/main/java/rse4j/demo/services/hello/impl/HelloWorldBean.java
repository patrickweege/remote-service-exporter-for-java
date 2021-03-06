package rse4j.demo.services.hello.impl;

import javax.ejb.Stateless;

import rse4j.demo.services.hello.HelloWorldBeanLocal;

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
