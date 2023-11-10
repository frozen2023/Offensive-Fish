package com.fish.offensivefish.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * AES加密字符串 * @param content 需要被加密的字符串
     *
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String password) {

        try {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 利用用户密码作为随机数初始化出128位的key生产者
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 修改后
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            // 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
            byte[] enCodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchPaddingException e) {
            logger.error("加密失败",e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("加密失败",e);
        } catch (InvalidKeyException e) {
            logger.error("加密失败",e);
        } catch (IllegalBlockSizeException e) {
            logger.error("加密失败",e);
        } catch (BadPaddingException e) {
            logger.error("加密失败",e);
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串 * @param content AES加密过过的内容
     *
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 修改后
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥
            byte[] enCodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            // 明文
            return result;
        } catch (NoSuchAlgorithmException e) {
            logger.error("解密失败",e);
        } catch (NoSuchPaddingException e) {
            logger.error("解密失败",e);
        } catch (InvalidKeyException e) {
            logger.error("解密失败",e);
        } catch (IllegalBlockSizeException e) {
            logger.error("解密失败",e);
        } catch (BadPaddingException e) {
            logger.error("解密失败",e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) {
        String content = "测试";
        String password = "";//密码就是key
        System.out.println("加密之前：" + content);
        // 加密
        byte[] encrypt = AESUtil.encrypt(content, password);
        System.out.println("加密后的内容：" + new String(encrypt));

        //如果想要加密内容不显示乱码，可以先将密文转换为16进制
        String hexStrResult = parseByte2HexStr(encrypt);
        System.out.println("16进制的密文：" + hexStrResult);

        //如果的到的是16进制密文，别忘了先转为2进制再解密
        byte[] twoStrResult = parseHexStr2Byte(hexStrResult);
        System.out.println(twoStrResult);
        // 解密
        byte[] decrypt = AESUtil.decrypt(twoStrResult, password);
        System.out.println(decrypt.toString());
        System.out.println("解密后的内容：" + new String(decrypt));
    }

}