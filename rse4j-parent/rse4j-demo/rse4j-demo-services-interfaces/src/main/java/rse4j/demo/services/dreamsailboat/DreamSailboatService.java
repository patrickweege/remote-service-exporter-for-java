package rse4j.demo.services.dreamsailboat;

import java.util.List;

public interface DreamSailboatService {

	public List<String> getBoatList();
	
	public String getFirst();
	
	public void addAt(String boat, int index);

}
