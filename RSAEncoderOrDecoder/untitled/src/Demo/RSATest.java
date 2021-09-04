package Demo;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class RSATest {
    private static final int KEY_LENGTH = 1024; //密钥长度
    private static final String ALGORITHM = "RSA"; //算法名称
    private static final String UTF8 = StandardCharsets.UTF_8.name();

    public static HashMap<Integer, String> map;

    public RSATest() {
        map = new HashMap<>();
    }

    public void getTwoKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
        generator.initialize(KEY_LENGTH); //初始密钥长度

        KeyPair keyPair = generator.generateKeyPair(); //生成密钥对
        RSAPrivateKey aPrivate = (RSAPrivateKey)keyPair.getPrivate(); //得到私钥
        RSAPublicKey aPublic = (RSAPublicKey)keyPair.getPublic(); //得到公钥

        byte[] encodedPrivate = aPrivate.getEncoded();
        String privateKeyStr = Base64.getEncoder().encodeToString(encodedPrivate); //以Base64的编码格式转换为字符串

        byte[] encodedPublic = aPublic.getEncoded();
        String publicKeyStr = Base64.getEncoder().encodeToString(encodedPublic);

        map.put(0, publicKeyStr); //将字符串存储在哈希表中
        map.put(1, privateKeyStr);
    }

    public String encrypt(String originStr, String key) throws Exception {
        //将key进行解码
        byte[] decoded = Base64.getDecoder().decode(key);
        RSAPublicKey publicKey = (RSAPublicKey)KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //初始化Cipher，进行加密算法过程
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] res = cipher.doFinal(originStr.getBytes(UTF8));

        return Base64.getEncoder().encodeToString(res); //Base64的编码格式，转换为字符串
    }

    public String decrypt(String originStr, String key) throws Exception {
        byte[] decode = Base64.getDecoder().decode(key);
        RSAPrivateKey privateKey = (RSAPrivateKey)KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decode));

        //通过Cipher进行解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] res = cipher.doFinal(Base64.getDecoder().decode(originStr.getBytes(UTF8)));

        return new String(res);
    }
}
