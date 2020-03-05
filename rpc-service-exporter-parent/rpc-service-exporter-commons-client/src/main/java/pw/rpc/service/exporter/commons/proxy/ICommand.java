package pw.rpc.service.exporter.commons.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface ICommand extends Serializable  {
	
	public Object execute(Method method, Object[] args) throws Throwable;
	
}
