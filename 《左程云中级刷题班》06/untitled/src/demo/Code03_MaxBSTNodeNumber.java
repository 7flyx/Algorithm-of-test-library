package demo;

/**
 * 找到一棵二叉树中，最大的搜索二叉子树，返回最大搜索二叉子树的节点个数。
 * @author Administrator
 */
public class Code03_MaxBSTNodeNumber {
	public static void main(String[] args) {
		TreeNode node = new TreeNode(8);
		node.left = new TreeNode(2);
		node.right = new TreeNode(6);
		node.left.left = new TreeNode(1);
		node.left.right = new TreeNode(9);
		node.right.left = new TreeNode(5);
		node.right.right = new TreeNode(7);
		
		System.out.println(calcBSTMaxNodeNumber(node));
	}
	
	private static class Info {
		boolean isBST;
		int maxNumber;
		int min, max;
		public Info(boolean isBST, int maxNumber, int min, int max) {
			this.isBST = isBST;
			this.maxNumber = maxNumber;
			this.max = max;
			this.min = min;
		}
	}
	
	public static int calcBSTMaxNodeNumber(TreeNode node) {
		if (node == null) {
			return 0;
		}
		
		return process(node).maxNumber;
	}
	
	public static Info process(TreeNode node) {
		if (node == null) {
			return new Info(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		
		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		//左右子树都是BST，并且node节点的大小满足条件
		if (leftInfo.isBST && rightInfo.isBST && 
				leftInfo.max < node.val && rightInfo.min > node.val) {
			return new Info(true, leftInfo.maxNumber + rightInfo.maxNumber + 1,
					Math.min(leftInfo.min,  node.val), Math.max(rightInfo.max, node.val));
		}
		
		//左右子树肯定有一个不是BST
		int maxNumber= Math.max(leftInfo.maxNumber , rightInfo.maxNumber);
		if (leftInfo.isBST && leftInfo.max < node.val) { //左子树是BST，且节点大小满足
			maxNumber = Math.max(maxNumber, leftInfo.maxNumber + 1);
		}
		if (rightInfo.isBST && rightInfo.min > node.val) {//右子树是BST，且节点大小满足
			maxNumber = Math.max(maxNumber, rightInfo.maxNumber + 1);
		}
		return new Info(false, maxNumber, 0, 0);
	}
}
