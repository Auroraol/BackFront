package com.zhang.musicrs.util.encrypUtil;

import jakarta.xml.bind.DatatypeConverter;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author ZhangChaojie
 * @Description: TODO(PBKDF2加密算法)
 * @date 2021/12/22 15:22
 * 参考地址：https://www.codenong.com/cs106288390/
 */
public class PBKDF2 {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    //盐的长度
    public static final int SALT_SIZE = 16;

    // 盐
    public static final String SALT = "0102030405060708";

    // 生成密文的长度
    public static final int HASH_SIZE = 32;

    // 迭代次数
    public static final int PBKDF2_ITERATIONS = 100;

    /**
     * 验证密码是否正确
     *
     * @param password   明文密码
     * @param cypherText 密文
     * @return 密码是否正确
     */
    public static boolean verify(String password, String cypherText) {
        // 使用相同的盐值对传入的密码进行加密，然后把加密的密文与传入的密文比较，相同则验证成功
        return getPBKDF2(password).equals(cypherText);
    }

    /**
     * 根据password和salt生成密文
     */
    public static String getPBKDF2(String password) {
        try {
            //将16进制字符串形式的salt转换成byte数组
            byte[] saltBytes = DatatypeConverter.parseHexBinary(SALT);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            // 得到加密后的字节数组
            byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
            //将byte数组转换为16进制的字符串
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e);
        }
        return "";
    }
    //
    // public static void main(String[] args) {
    //     // 显示盐值
    //     System.out.println("salt:" + SALT);
    //     // 给明文密码加密
    //     String cypherText = getPBKDF2("123");
    //     System.out.println("密文:" + cypherText);
    //     System.out.println("盐值+密文:" + SALT + "\t" + cypherText);
    //     // 验证密码
    //     System.out.println(verify("123", cypherText));
    // }
}
