package demo;

/**
 * 给定两个数组，可能等长，也可能不等长,并且两个数组都是有序的。 请返回最小的第K个数，问如何实现最快的解法。
 * 
 * @author Administrator
 */
public class Code01_TwoArrayTOPK {
	public static void main(String[] args) {
		int[] arr1 = { 1, 3, 7, 8, 9, 11 };
		int[] arr2 = { 2, 4, 6, 8, 10, 12 ,18, 19, 20};
		//System.out.println(getMidAndUpNum(arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1));
		System.out.println(getTopK(arr1, arr2, 12));
	}

	public static int getTopK(int[] arr1, int[] arr2, int k) {
		if (arr1 == null || arr2 == null || k < 1 || k > arr1.length + arr2.length) {
			throw new RuntimeException("输入参数非法");
		}

		// 首先需要判断k在以下哪个范围内：
		// 1. 1 <= k <= 短数组的长度
		// 2. 短数组的长度 < k <= 长数组的长度
		// 3. 长数组的长度 < k <= 短数组+长数组的总长度
		int l = Math.max(arr1.length, arr2.length); // 长数组
		int s = Math.min(arr1.length, arr2.length); // 短数组
		int[] longs = l == arr1.length? arr1 :arr2;
		int[] shorts = s == arr1.length? arr1 : arr2;
		if (k <= s) { // 第一个范围内，小于s的。直接调用算法原型
			return getMidAndUpNum(arr1, 0, k - 1, arr2, 0, k - 1);
		}

		if (k > s && k <= l) { // 第二个范围内，大于s，小于l
			int L2 = k - s - 1; //长数组的左边界
			int R2 = k - 1; //长数组的右边界
			return getMidAndUpNum(shorts, 0, s, longs, L2, R2);
		}

		// 最后就是第三个范围内的，大于l，小于l+s的
		//这样一个范围的数，不一定是答案，有歧义。始终算出来的结果少了一个数
		//需要分别自己手动判断一下两个数组的第一个数是否就是答案，不是的话，直接pass掉
		int L1 = k - l - 1; //第一个数组的左边界
		int L2 = k - s - 1; //第二个数组的左边界
		int R2 = longs.length - 1;
		if (shorts[L1] >= longs[l - 1]) {//第一个数组的第一个数
			return shorts[L1];
		}
		if (longs[L2] >= shorts[s - 1]) { //第二个数组的第一个数
			return longs[L2];
		}
		return getMidAndUpNum(shorts, L1 + 1, s - 1, longs, L2 + 1, R2);
	}

	// 调用该方法的前提是，两个数组的长度相等。
	// 该方法会返回两个数组合并之后的上中位数
	public static int getMidAndUpNum(int[] arr1, int L1, int R1, int[] arr2, int L2, int R2) {
		if (R1 - L1 != R2 - L2) {
			throw new RuntimeException("两个数组长度不相等");
		}

		int mid1 = (L1 + R1) / 2;
		int mid2 = (L2 + R2) / 2;
		if (arr1[mid1] == arr2[mid2]) { // 中位数相等，说明这就是整体的上中位数
			return arr1[mid1];
		}

		if (((R1 - L1 + 1) & 1) == 0) { // 偶数情况
			if (arr1[mid1] > arr2[mid2]) {
				R1 = mid1;
				L2 = mid2 + 1;
			} else {
				R2 = mid2;
				L1 = mid1 + 1;
			}
		} else { // 奇数情况
			if (arr1[mid1] > arr2[mid2]) {
				R1 = mid1 - 1; // mid1的右边不可能了
				if (arr2[mid2] >= arr1[R1]) { // 人为判断一下，因为第2个数组多出来了一个数
					return arr2[mid2];
				}
				L2 = mid2 + 1; // 第2个数组的右边
			} else {
				R2 = mid2 - 1;
				if (arr1[mid1] >= arr2[R2]) {// 人为判断一下，第一个数组多出来一个数
					return arr1[mid1];
				}
				L1 = mid1 + 1;
			}
		}
		return getMidAndUpNum(arr1, L1, R1, arr2, L2, R2);
	}
}
