/**
 * Created by flyx
 * Description:  测试前缀树
 * User: 听风
 * Date: 2021-09-01
 * Time: 16:38
 */
public class Demo {
    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        tree.add("abcd");
        tree.add("abef");
        tree.add("abcq");
        tree.add("bcde");
        tree.add("bcd");
        tree.add("cder");

        System.out.println(tree.delete("abef"));
        System.out.println(tree.delete("abe"));
    }
}
