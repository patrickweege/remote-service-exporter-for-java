package test.proxy;

public class ProxyTest1 {

	public static void main(String[] args) {

		IHelloFacade facade = (IHelloFacade) DebugProxy.newInstance(IHelloFacade.class);
	    facade.sayHello("Patrick");
	    
	}

}
