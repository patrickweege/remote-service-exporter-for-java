package rpc.service.exporter.test.services.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeNode implements Serializable{

	protected List<TreeNode> children;
	private TreeNode parent;
	private String name;

	public TreeNode(String name) {
		this.children = new ArrayList<TreeNode>();
		this.name = name;
	}

	public List<TreeNode> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	public TreeNode getParent() {
		return null;
	}

	public void setParent(TreeNode theParent) {
		this.parent = theParent;
	}

	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	public void add(TreeNode child) {
		this.children.add(child);
		child.setParent(this);
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		String desc = "";
		TreeNode current = this;
		while (current != null) {
			desc = current.getName() + "-" + desc;
			current = current.getParent();
		}
		return desc;
	}

}
