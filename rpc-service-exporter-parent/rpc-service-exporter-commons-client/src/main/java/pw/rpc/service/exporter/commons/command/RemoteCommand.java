package pw.rpc.service.exporter.commons.command;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;

import pw.rpc.service.exporter.commons.proxy.ICommand;

public class RemoteCommand implements ICommand {

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
