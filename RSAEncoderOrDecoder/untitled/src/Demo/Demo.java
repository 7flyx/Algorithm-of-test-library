package Demo;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-09-04
 * Time: 16:32
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        RSATest rsa = new RSATest();
        rsa.getTwoKey();
        String str = "重庆三峡";
        String encrypted = rsa.encrypt(str, RSATest.map.get(0));
        System.out.println("加密后的结果: " + encrypted);
        System.out.println("=============");

        String decrypted = rsa.decrypt(encrypted, RSATest.map.get(1));
        System.out.println("解密后的结果: " + decrypted);
    }
}
