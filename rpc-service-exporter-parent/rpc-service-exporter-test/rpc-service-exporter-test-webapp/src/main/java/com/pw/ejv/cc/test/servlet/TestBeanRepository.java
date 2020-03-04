package com.pw.ejv.cc.test.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestBeanRepository {

	private static final TestBeanRepository instance = new TestBeanRepository();

	private final Map<String, Object> ejbMap;

	public static TestBeanRepository getInstance() {
		return instance;
	}

	private TestBeanRepository() {
		this.ejbMap = new HashMap<>();

		ArrayList<String> l = new ArrayList<>();
		l.add("Hallo");
		l.add("Meister");

		ejbMap.put("ejb/meisterlist", l);
	}

	public Object lookup(String beanName) {
		return ejbMap.get(beanName);
	}
}
