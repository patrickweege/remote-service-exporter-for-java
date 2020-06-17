package rpc.service.exporter.test.services.tree;

public interface TreeService {

	public TreeNode getRootTreeNode();

	public TreeNode getBigTree(int nodesOnLevel, int levels);

}
