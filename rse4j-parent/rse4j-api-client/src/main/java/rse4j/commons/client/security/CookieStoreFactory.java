package rse4j.commons.client.security;

import org.apache.http.client.CookieStore;

public class CookieStoreFactory {

	private static CookieStoreFactory INSTANCE;
	
	private ICookieStoreProvider cookieStoreProvider;

	private CookieStoreFactory() {
		
	}
	
	public static CookieStoreFactory getInstance() {
		if(INSTANCE == null) {
			synchronized (CookieStoreFactory.class) {
				if(INSTANCE == null) {
					INSTANCE = new CookieStoreFactory();
				}
			}
		}
		return INSTANCE; 
	}
	
	public void setCookieStoreProvider(ICookieStoreProvider cookieStoreProvider) {
		this.cookieStoreProvider = cookieStoreProvider;
	}

	public CookieStore getCookieStore() {
		return this.cookieStoreProvider.getCookieStore();
	}
	
}
