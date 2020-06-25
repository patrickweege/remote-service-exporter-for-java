package rse4j.commons.client.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatorProxy implements InvocationHandler, Serializable {

	private static final long serialVersionUID = -6750719074583123248L;
	
	private static final transient Logger LOGGER = LoggerFactory.getLogger(DelegatorProxy.class);

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
		LOGGER.debug("DelegatorProxy.invoke() for Method {}" + method.getName());
		return delegateCommand.execute(method, args);
	}

}
