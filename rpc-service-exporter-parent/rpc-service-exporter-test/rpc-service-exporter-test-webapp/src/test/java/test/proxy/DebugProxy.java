package test.proxy;

import java.lang.reflect.Method;

public class DebugProxy implements java.lang.reflect.InvocationHandler {

	private Object obj;

	public static Object newInstance(Class clazz) {
		
		Class[] interfaces = new Class[] {clazz};
		
		return java.lang.reflect.Proxy.newProxyInstance( //
				clazz.getClassLoader(), //
				interfaces, //
				new DebugProxy(null));
	}

	private DebugProxy(Object obj) {
		this.obj = obj;
	}

	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		Object result = null;
		try {
			System.out.println("before method " + m.getName());
			int i = 0;
			for (Object argument : args) {
				System.out.println("args[" + i + "] = " + argument);
			}

			// result = m.invoke(obj, args);
//		} catch (InvocationTargetException e) {
//			throw e.getTargetException();
		} catch (Exception e) {
			throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
		} finally {
			System.out.println("after method " + m.getName());
		}
		return result;
	}
}