package test.rpc.service.exporter.test.services.tree.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import rpc.service.exporter.test.services.tree.TreeNode;
import rpc.service.exporter.test.services.tree.impl.TreeDatasource;

public class TreeDatasourceTest {

	@Test
	void testGetBigTree() {
		
		TreeDatasource ds = new TreeDatasource();
		
		TreeNode bigTree = ds.getBigTree(8, 5);
		
		System.out.println(bigTree);
		
		assertNotNull(bigTree);
		
	}

}
