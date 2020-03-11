package rpc.service.exporter.test.services.tree.impl;

import rpc.service.exporter.test.services.tree.RootTreeNode;
import rpc.service.exporter.test.services.tree.TreeNode;

public class TreeDatasource {

	public RootTreeNode getTree() {

		RootTreeNode root = new RootTreeNode();
		
		TreeNode nodeA = new TreeNode("A");
		root.add(nodeA);
		
		TreeNode nodeAA = new TreeNode("AA");
		nodeA.add(nodeAA);
		
		TreeNode nodeAAA = new TreeNode("AAA");
		nodeAA.add(nodeAAA);

		
		TreeNode nodeB = new TreeNode("B");
		root.add(nodeB);
		
		TreeNode nodeBB = new TreeNode("BB");
		nodeB.add(nodeBB);
		
		TreeNode nodeBBB1 = new TreeNode("BBB/1");
		nodeBB.add(nodeBBB1);

		TreeNode nodeBBB2 = new TreeNode("BBB/2");
		nodeBB.add(nodeBBB2);
		
		return root;
		
	}
	
}
