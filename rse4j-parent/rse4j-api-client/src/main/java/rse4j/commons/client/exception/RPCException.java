package rse4j.commons.client.exception;

public class RPCException extends RuntimeException {

	private static final long serialVersionUID = 7468060757203123290L;
	
	
	public RPCException(Throwable e) {
		super(e);
	}
	
}
