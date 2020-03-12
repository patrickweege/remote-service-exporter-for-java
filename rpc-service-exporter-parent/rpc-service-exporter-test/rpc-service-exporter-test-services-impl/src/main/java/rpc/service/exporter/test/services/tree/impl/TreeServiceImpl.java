package rpc.service.exporter.test.services.tree.impl;

import rpc.service.exporter.test.services.tree.TreeNode;
import rpc.service.exporter.test.services.tree.TreeService;

public class TreeServiceImpl implements TreeService {

	private final TreeDatasource treeDatasource;

	public TreeServiceImpl() {
		this.treeDatasource = new TreeDatasource();
	}
	
	@Override
	public TreeNode getRootTreeNode() {
		return treeDatasource.getTree();
	}

	@Override
	public TreeNode getBigTree(int nodesOnLevel, int levels) {
		return treeDatasource.getBigTree(nodesOnLevel, levels);
	}

}
