package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:23
 * Description:电话号的组合方式
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code07_PhoneNumberCombine {
    class Solution {
        public List<String> letterCombinations(String digits) {
            if(digits == null || digits.equals("")) {
                return new ArrayList<String>();
            }
            String[] str = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            List<String> res = new ArrayList<>();
            char[] tmp = new char[digits.length()]; //通过一个临时的数组，存储字符串
            dfs(str, digits.toCharArray(), res, tmp, 0);
            return res;
        }

        private void dfs(String[] str, char[] digits, List<String> res, char[] tmp, int index) {
            if (index == digits.length) {
                res.add(new String(tmp)); //当tmp数组的字符，转换为字符串
                return;
            }
            int number = digits[index] - '0';
            char[] chs = str[number - 2].toCharArray();
            for (char ch : chs) {
                tmp[index] = ch;
                dfs(str, digits, res, tmp, index + 1);
            }
        }
    }
}
