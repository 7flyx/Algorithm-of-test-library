package demo;

/**
 * 给定一个数组arr[1,1,-1,-10,11,4,-6,9,20,-10,-2], 计算子数组的最大累加和。
 * 
 * @author Administrator
 *
 */
public class Code06_SubArrayMaxSum {
	
	public static void main(String[] args) {
		int[] arr = {1,1,-1,-10,11,4,-6,9,20,-10,-2};
		System.out.println(calcSubArrayMaxSum(arr));
	}
	
	//计算子数组的最大累加和
	public static int calcSubArrayMaxSum(int[] tmp) {
		int sum = 0;
		int max = 0;
		for (int num : tmp) {
			sum += num;
			max = Math.max(max, sum);
			sum = sum < 0? 0 : sum;
		}
		return max;
	}
}
