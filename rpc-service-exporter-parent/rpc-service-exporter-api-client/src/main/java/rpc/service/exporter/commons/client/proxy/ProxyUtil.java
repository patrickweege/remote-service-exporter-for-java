package rpc.service.exporter.commons.client.proxy;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProxyUtil {
	
	public static <T extends Object> T getRemoteProxy(Object remoteObject, ICommand delegateCommand) {
		Collection<Class<?>> interfaces = ProxyUtil.getInterfaces(remoteObject.getClass());
		
		
		return DelegatorProxy.getProxyInstance(delegateCommand, interfaces.toArray(new Class<?>[0]));
	}
	
	private static Collection<Class<?>> getInterfaces(Class<?> clazz) {
		Set<Class<?>> interfaces = new HashSet<>();

		if (clazz == null) {
			throw new IllegalArgumentException("The 'clazz' argument cannot be null");
		}
		if (clazz.isInterface()) {
			interfaces.add(clazz);
		} else {
			Class<?>[] classInterfaces = clazz.getInterfaces();
			interfaces.addAll(Arrays.asList(classInterfaces));
		}
		
		return interfaces;
	}

}
