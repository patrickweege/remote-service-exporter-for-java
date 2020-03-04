package com.pw.ejb.cc.test.ejb;

import javax.ejb.Local;

@Local
public interface HelloWorldBeanLocal {

	public String sayHello(String name);

}
