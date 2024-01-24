package com.example.springbootredissontest1.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author Janwes
 * @version 1.0.0
 * @package com.janwes.utils
 * @date 2021/9/19 14:44
 * @description 对称加密算法  AES加密和解密
 */
public class SecurityUtil {

    /**
     * AES加密算法
     */
    private static final String AES_ALGORITHM = "AES";

    /**
     * 随机算法种子
     */
    private static final String SEED = "chinagdszbaoan";

    /**
     * 密码加密
     *
     * @param source
     * @return
     */
    public static String encrypt(String source) {
        try {
            byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = generateSKey();
            // 生成一个Cipher密码器对象基于AES加密算法进行加密
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 对Cipher对象初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 加密数据生成密文
            byte[] bytes = cipher.doFinal(sourceBytes);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("encrypt message [" + source + "] failure...", e);
        }
    }

    /**
     * 密码解密
     *
     * @param source
     * @return
     */
    public static String decrypt(String source) {
        try {
            byte[] sourceBytes = Base64.decodeBase64(source);
            SecretKey secretKey = generateSKey();
            // 生成一个Cipher密码器对象基于AES加密算法进行加密
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 对Cipher对象初始化
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 加密数据生成密文
            byte[] bytes = cipher.doFinal(sourceBytes);
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("decrypt message [" + source + "] failure...", e);
        }
    }

    /**
     * 生成密钥
     *
     * @return
     */
    private static SecretKey generateSKey() {
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            // 设置随机加密算法SHA1PRNG
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 设置随机种子
            secureRandom.setSeed(SEED.getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(128, secureRandom);
            // 生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] encoded = secretKey.getEncoded();
            return new SecretKeySpec(encoded, AES_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("generate secret key failure...", e);
        }
    }

    /**
     * 初始化生成密钥
     *
     * @return
     */
    public static String initSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("init generate secret key failure...", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("741106"));
        System.out.println(encrypt("123456"));
    }
}
