package pw.rpc.service.exporter.commons.command;

import java.lang.reflect.Method;

import pw.rpc.service.exporter.commons.proxy.ICommand;

public class RemoteCommand implements ICommand {

	private final String rpcEndPoint;
	private final String rpcBeanReference;

	public RemoteCommand(String rpcEndPoint, String rpcBeanReference) {
		this.rpcEndPoint = rpcEndPoint;
		this.rpcBeanReference = rpcBeanReference;
	}

	@Override
	public Object execute(Method method, Object[] args) throws Throwable {
		System.out.println("Call: " + rpcEndPoint + "/" + rpcBeanReference + "/" + method.getName());
		for (int i = 0; i < args.length; i++) {
			System.out.println("ARGS[" + i + "] = " + args[i]);
		}

		RPCMessage rpcMessage = new RPCMessage(rpcEndPoint, rpcBeanReference, method, args);
		return rpcMessage.executeRPCCall();

	}

}
