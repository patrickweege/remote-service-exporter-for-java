package rpc.service.exporter.commons.client.exception;

public class RPCRemoteException extends RuntimeException {

	private static final long serialVersionUID = -975374103368955972L;

	public static final int STATUS_CODE_RPCEXCEPTION = 599;

	public RPCRemoteException(Exception e) {
		super(e);
// TODO If the Exception/Cause canot be Serialized/Deserialized. Then use Plain Stacktrace		
//		super();
//		List<StackTraceElement> plainStackTrace = new ArrayList<>();
//
//		for (Throwable t : ExceptionUtils.getThrowableList(e)) {
//			Arrays.stream(t.getStackTrace()).forEach(elem -> plainStackTrace.add(elem));
//		}
//
//		super.setStackTrace(plainStackTrace.toArray(new StackTraceElement[0]));

	}

}
