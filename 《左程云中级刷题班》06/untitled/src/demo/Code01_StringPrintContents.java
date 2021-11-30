package demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 给你一个字符串类型的数组arr，譬如： String[] arr = { "b\\cst", "d\\", "a\\d\\e", "a\\b\\c" };
 * 你把这些路径中蕴含的目录结构给画出来，子目录直接列在父目录下面，并比父目录 向右进两格，就像这样:
 * 
 * 同一级的需要按字母顺序排列，不能乱。
 * 
 * @author Administrator
 *
 */

public class Code01_StringPrintContents {
	public static void main(String[] args) {
		String[] arr = { "b\\cst", "d\\", "a\\d\\e", "a\\b\\c" };
		TrieTree tree = new TrieTree();
		for (String string : arr) {
			tree.add(string.split("\\\\"));
		}
		tree.dfs();
	}

	public static void stringPrint(String[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		// 根据数组的数据，建立前缀树

	}

	private static class Node {
		int start, end;
		String string = "";
		HashMap<String, Node> nexts;

		public Node(String s) {
			string = s;
			nexts = new HashMap<>();
		}

		public Node() {
			nexts = new HashMap<>();
		}
	}

	private static class TrieTree {
		Node root = null;

		public TrieTree() {
			root = new Node();
		}

		public void add(String[] ch) {
			if (ch == null || ch.length == 0) {
				return;
			}

			Node node = root;
			node.start++;
			for (String s : ch) {
				if (!node.nexts.containsKey(s)) { // 不包含当前字符串的值
					node.nexts.put(s, new Node(s));
				}
				node = node.nexts.get(s);
				node.start++;
			}
			node.end++;
		}

		public void dfs() {
			Node node = root;
			if (node.start == 0) {
				return;
			}
			dfs(node, 1);
		}

		private void dfs(Node node, int floor) {
			if (node == null) {
				return;
			}

			String string = node.string;
			if (floor != 1 ) {
				getSpace(string, floor);
			}
			Set<Entry<String, Node>> entrySet = node.nexts.entrySet();
			for (Entry<String, Node> set : entrySet) {
				dfs(set.getValue(), floor + 1);
			}
		}

		private void getSpace(String string, int floor) {
			StringBuilder string2 = new StringBuilder();
			while (--floor > 1) {
				string2.append("  ");
			}
			System.out.println(string2.toString() + string);
		}
	}

}
