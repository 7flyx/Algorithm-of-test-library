package demo;
/**
 * 约瑟夫环问题，常规的解法就是一个个去数M，所以时间复杂度是O(N*M)。
 * 以下的解法是一个O(N)的解法。通过公式推导相应的关系。
 * 假设已知长度是5的链表中，存活的节点是3，M=3，根据这几个参数推导长度是6的，
 * 存活节点是几
 * @author Administrator
 */
public class Code02_JosephusProblem {
	public static void main(String[] args) {
		int N = 5; //链表的总长度
		int M = 3; //报数到3，就杀掉
		System.out.println(calcLiveNum(N, M));
	}
	
	public static int calcLiveNum(int N, int M) {
		if (N == 1) { //只剩一个节点的时候，就是这个节点能够活下来
			return 1;
		}
		
		//公式：y = （x + m - 1）% i + 1
		//y指的是老编号，x指的是新编号
		//这里的x和y都是指存活下来那个节点
		//m就是报数，
		//i指的是当前的总人数
		return (calcLiveNum(N - 1, M) + M - 1) % N + 1;
	}
	
	
}
