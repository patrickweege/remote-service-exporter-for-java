package rpc.service.exporter.commons.client.data;

import java.io.Serializable;

public final class Null implements Serializable {
	
	private static final long serialVersionUID = -7781523180176798144L;
	
	public static final Null NULL = new Null();
	
    private Null() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(obj.getClass().equals(Null.class)) return true;
    	return false;
    }
    

}
