package rpc.service.exporter.commons.client.command;

import java.lang.reflect.Method;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rpc.service.exporter.commons.client.proxy.ICommand;

public class RemoteCommand implements ICommand {

	private static final long serialVersionUID = -3422902019586686684L;
	
	private static final transient Logger LOGGER = LoggerFactory.getLogger(RemoteCommand.class);
	
	private final URL rpcInvokeBaseURL;

	public RemoteCommand(URL rpcInvokeBaseURL) {
		this.rpcInvokeBaseURL = rpcInvokeBaseURL;
	}

	@Override
	public Object execute(Method method, Object[] args) throws Throwable {
		LOGGER.debug("RemoteCommand.execute() of Method {}" + method.getName());
		RPCMessage rpcMessage = new RPCMessage(rpcInvokeBaseURL, method, args);
		return rpcMessage.executeRPCCall();

	}

}
