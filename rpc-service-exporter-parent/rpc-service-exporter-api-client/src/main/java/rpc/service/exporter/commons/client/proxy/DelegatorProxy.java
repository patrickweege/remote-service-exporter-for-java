package rpc.service.exporter.commons.client.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DelegatorProxy implements InvocationHandler, Serializable {

	private final ICommand delegateCommand;

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getProxyInstance(ICommand command, Class<?>... interfaces) {
		return (T) java.lang.reflect.Proxy.newProxyInstance(
				DelegatorProxy.class.getClassLoader(),
	            interfaces,
	            new DelegatorProxy(command));
	}

	public DelegatorProxy(ICommand delegateCommand) {
		this.delegateCommand = delegateCommand;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return delegateCommand.execute(method, args);
	}

}
