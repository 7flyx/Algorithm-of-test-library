package demo;

/**
 * 双向链表节点结构和二叉树节点结构是一样的，如果你把last认为是left， next认为是next的话。
 * 给定一个搜索二叉树的头节点head，请转化成一条有序的双向链表，并返回链 表的头节点。
 * 
 * @author Administrator
 *
 */

class TreeNode {
	int val;
	TreeNode left, right;
	public TreeNode(int val) {
		this.val = val;
	}
}

public class Code02_SBTToLinkedList {
	
	private TreeNode preNode = null;
	
	public static void main(String[] args) {
		TreeNode node = new TreeNode(4);
		node.left = new TreeNode(2);
		node.right = new TreeNode(6);
		node.left.left = new TreeNode(1);
		node.left.right = new TreeNode(3);
		node.right.left = new TreeNode(5);
		node.right.right = new TreeNode(7);
		
		TreeNode headNode = treeToLinkedList2(node);
		while (headNode != null) {
			System.out.print(headNode.val + " ");
			headNode = headNode.right;
		}
		
		
	}
	
	
	private static class Info {
		TreeNode head, tail;
		public Info(TreeNode head, TreeNode tail) {
			this.head = head;
			this.tail = tail;
		}
	}
	
	public static TreeNode treeToLinkedList1(TreeNode node) {
		if (node == null) {
			return null;
		}
		
		return process(node).head;
	}
	
	public static Info process(TreeNode node) {
		if (node == null) {
			return new Info(null,  null);
		}
		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		node.left = leftInfo.tail;
		node.right = rightInfo.head;
		if (leftInfo.tail != null) {
			leftInfo.tail.right = node;
		}
		if(rightInfo.head != null) {
			rightInfo.head.left = node;
		}
		
		return new Info(leftInfo.head != null? leftInfo.head : node,
				rightInfo.tail != null? rightInfo.tail : node);
	}
	
	private void name() {
		
	}
	
	public static TreeNode treeToLinkedList2(TreeNode node) {
		if (node == null) {
			return null;
		}
		TreeNode[] arr = {null};
		treeToLinkedList2(node, arr);
		TreeNode preNode = arr[0];
		while (preNode != null && preNode.left != null) {
			preNode = preNode.left;
		}
		return preNode;
	}
	
	public static void treeToLinkedList2(TreeNode node, TreeNode[] pre) {
		if (node == null) {
			return;
		}
		if (node.left != null) {
			treeToLinkedList2(node.left, pre);
		}
		
		node.left = pre[0];
		if (pre[0] != null) {
			pre[0].right = node;
		}
		pre[0] = node;
		if (node.right != null) {
			treeToLinkedList2(node.right, pre);
		}
		
	}
	
}
