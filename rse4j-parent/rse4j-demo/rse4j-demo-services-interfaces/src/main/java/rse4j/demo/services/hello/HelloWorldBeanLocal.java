package rse4j.demo.services.hello;

import javax.ejb.Local;

@Local
public interface HelloWorldBeanLocal {

	public String sayHello(String name);

}
