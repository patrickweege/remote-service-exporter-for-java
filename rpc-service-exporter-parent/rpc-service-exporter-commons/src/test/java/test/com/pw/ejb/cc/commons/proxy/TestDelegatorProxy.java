package test.com.pw.ejb.cc.commons.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.pw.ejb.cc.commons.proxy.DelegatorProxy;
import com.pw.ejb.cc.commons.proxy.ICommand;

public class TestDelegatorProxy {

	@Test
	public void testGetString() {

		IGetString getStringimpl = new IGetString() {

			@Override
			public String getString(String strToReturn) {
				return strToReturn;
			}
		};

		IGetString getStringProxy = (IGetString) this.getTestProxy(getStringimpl);

		String strToGet = "Hallo Meister";

		assertEquals( //
				getStringimpl.getString(strToGet), //
				getStringProxy.getString(strToGet));

	}

	@Test
	public void testProxyToList() {
		List<String> aList = new ArrayList<>();
		aList.add("Hallo");
		aList.add("Meister");

		List<String> listProxy = (List<String>) this.getTestProxy(aList);
		
		assertEquals(aList.get(0), listProxy.get(0));
		
		assertEquals(aList.get(1), listProxy.get(1));
		
	}

	private <T extends Object> T getTestProxy(T obj) {

		@SuppressWarnings("unchecked")
		T proxyInstance = (T) DelegatorProxy.getProxyInstance(//
				new TestDelegatorProxyCommand(obj), //
				obj.getClass().getInterfaces());
		
		return proxyInstance;

	}

	public interface IGetString {

		public String getString(String strToReturn);

	}

	/**
	 * 
	 * 
	 * Delegate Command for Test that delegates the invocation to the internal .
	 * 
	 * @author patrickweege
	 *
	 */
	public class TestDelegatorProxyCommand implements ICommand {

		Object delegateImpl;

		public TestDelegatorProxyCommand(Object delegateImpl) {
			this.delegateImpl = delegateImpl;
		}

		@Override
		public Object execute(Method method, Object[] args) throws Throwable {
			return method.invoke(delegateImpl, args);
		}
	}

}
