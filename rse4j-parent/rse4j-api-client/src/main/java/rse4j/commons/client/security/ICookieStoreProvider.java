package rse4j.commons.client.security;

import org.apache.http.client.CookieStore;

public interface ICookieStoreProvider {

	public CookieStore getCookieStore();
	
}
