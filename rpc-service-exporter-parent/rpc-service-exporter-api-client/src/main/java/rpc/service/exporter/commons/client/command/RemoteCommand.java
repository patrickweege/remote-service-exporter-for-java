package rpc.service.exporter.commons.client.command;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;

import rpc.service.exporter.commons.client.proxy.ICommand;

public class RemoteCommand implements ICommand {

	private static final long serialVersionUID = -3422902019586686684L;
	
	private final String rpcServiceEndPoint;

	public RemoteCommand(String rpcServiceEndPoint) {
		this.rpcServiceEndPoint = rpcServiceEndPoint;
	}

	@Override
	public Object execute(Method method, Object[] args) throws Throwable {
		System.out.println("Call: " + rpcServiceEndPoint + "/" + method.getName());
		if (!ArrayUtils.isEmpty(args)) {
			for (int i = 0; i < args.length; i++) {
				System.out.println("ARGS[" + i + "] = " + args[i]);
			}
		}
		RPCMessage rpcMessage = new RPCMessage(rpcServiceEndPoint, method, args);
		return rpcMessage.executeRPCCall();

	}

}
