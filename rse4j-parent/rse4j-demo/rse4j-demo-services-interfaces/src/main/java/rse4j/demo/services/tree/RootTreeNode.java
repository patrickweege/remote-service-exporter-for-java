package rse4j.demo.services.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RootTreeNode extends TreeNode {

	public RootTreeNode() {
		super("ROOT");
	}
	
	@Override
	public List<TreeNode> getChildren() {
		return Collections.unmodifiableList(this.children) ;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	@Override
	public void add(TreeNode child) {
		this.children.add(child);
		child.setParent(this);
	}

	@Override
	public void setParent(TreeNode child) {
		throw new UnsupportedOperationException("Cannot set Parent to ROOT");
	}
	
	@Override
	public String getName() {
		return "ROOT";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.printTree(this, sb, "");
		return sb.toString();
	}
	
	private void printTree(TreeNode node, StringBuilder sb, String tab) {
		sb.append(tab);
		sb.append(node.getName());
		sb.append("\n");
		String newTab = tab + "...";
		for (TreeNode child : node.getChildren()) {
			this.printTree(child, sb, newTab);
		}
	}
	
	
	
}
