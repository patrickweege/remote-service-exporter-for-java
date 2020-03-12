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

	public TreeNode getBigTree(int nodesOnLevel, int levels) {
		RootTreeNode root = new RootTreeNode();
		createTree(root, nodesOnLevel, 1, levels, 0);
		return root;
	}

	private int createTree(TreeNode parent, int nodesOnLevel, int level, int levels, int nodeCount) {
		for (int i = 0; i < nodesOnLevel; i++) {
			nodeCount++;
			TreeNode child = new TreeNode("Node ID: " + nodeCount + " - " + i + "/" + level);
			parent.add(child);
			if (level < levels) {
				nodeCount = createTree(child, nodesOnLevel, level + 1, levels, nodeCount);
			}
		}
		return nodeCount;
	}

}
