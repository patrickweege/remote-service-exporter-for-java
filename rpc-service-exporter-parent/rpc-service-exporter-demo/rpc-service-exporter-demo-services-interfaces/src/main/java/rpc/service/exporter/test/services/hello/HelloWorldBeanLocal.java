package rpc.service.exporter.test.services.hello;

import javax.ejb.Local;

@Local
public interface HelloWorldBeanLocal {

	public String sayHello(String name);

}
