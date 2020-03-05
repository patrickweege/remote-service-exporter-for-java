package pw.rpc.exporter.test.services;

import javax.ejb.Local;

@Local
public interface HelloWorldBeanLocal {

	public String sayHello(String name);

}
