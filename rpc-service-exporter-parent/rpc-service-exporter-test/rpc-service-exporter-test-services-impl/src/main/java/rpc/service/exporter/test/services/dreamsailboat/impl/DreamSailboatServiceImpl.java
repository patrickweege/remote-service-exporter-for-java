package rpc.service.exporter.test.services.dreamsailboat.impl;

import java.util.ArrayList;
import java.util.List;

import rpc.service.exporter.test.services.dreamsailboat.DreamSailboatService;

/**
 * Session Bean implementation class HelloWorldBean
 */
public class DreamSailboatServiceImpl implements DreamSailboatService {

	private List<String> sailboatList;

	public DreamSailboatServiceImpl() {
		this.sailboatList = new ArrayList<>();
		sailboatList.add("Gunboat");
		sailboatList.add("Amel-60ft");
		sailboatList.add("Sirius-DS-40ft");
	}
	
	@Override
	public List<String> getBoatList() {
		return sailboatList;
	}

	@Override
	public String getFirst() {
		if(sailboatList.isEmpty()) {
			return null;
		}
		return sailboatList.get(0);
	}

	@Override
	public void addAt(String boat, int index) {
		sailboatList.add(index, boat);
	}


}
