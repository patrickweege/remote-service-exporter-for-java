package test.rse4j.demo.services.tree.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import rse4j.demo.services.tree.TreeNode;
import rse4j.demo.services.tree.impl.TreeDatasource;

public class TreeDatasourceTest {

	@Test
	void testGetBigTree() {
		
		TreeDatasource ds = new TreeDatasource();
		
		TreeNode bigTree = ds.getBigTree(8, 5);
		
		System.out.println(bigTree);
		
		assertNotNull(bigTree);
		
	}

}
