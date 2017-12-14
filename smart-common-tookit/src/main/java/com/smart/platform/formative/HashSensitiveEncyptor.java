/**   
 * @Title: HashSensitiveEncyptor.java 
 * @Package: com.smart.platform.formative 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年8月10日 上午12:53:43 
 * @version 1.3.1 
 */

package com.smart.platform.formative;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.smart.platform.toolkit.Hex;

/**
 * @Description
 * @author Administrator
 * @date 2016年8月10日 上午12:53:43
 * @version V1.3.1
 */

public class HashSensitiveEncyptor
{
    private final static int ITER_KEY_LEN = 256;
    
    private final static int ITER_KEY_TIMES = 1024;
    
    public static byte[] HMACSHA256(byte[] data, byte[] key)
    {
        byte[] bytes = null;
        try
        {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            bytes = mac.doFinal(data);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new RuntimeException("No algorithm or invalid key exception:" + e.toString());
        }
        return bytes;
    }
    
    public static String HMACSHA256Str(byte[] data, byte[] key)
    {
        byte[] bytes = HMACSHA256(data, key);
        return (bytes == null) ? null : Hex.toHex(bytes);
    }
    
    public static String HMACSHA256Base64(String encodeData, String key)
    {
        byte[] bytes = HMACSHA256(encodeData.getBytes(), key.getBytes());
        return (bytes == null) ? null : Base64.encodeBase64String(bytes);
    }
    
    /**
     * 对字符串进行MD5进行加密处理
     * 
     * @param msg 待加密的字符串
     * @return 加密后字符串
     */
    public static String encryptMD5(String msg)
    {
        return encrypt(msg, null);
    }
    
    /**
     * 盐值的原理非常简单，就是先把密码和盐值指定的内容合并在一起，再使用md5对合并后的内容进行演算， 这样一来，就算密码是一个很常见的字符串，再加上用户名，最后算出来的md5值就没那么容易猜出来了。
     * 因为攻击者不知道盐值的值，也很难反算出密码原文。
     * 
     * @param msg
     * @return
     */
    public static String md5WithSalt(String msg)
    {
        return encrypt(msg, getSalt());
    }
    
    /**
     * SHA（Secure Hash Algorithm，安全散列算法）是消息摘要算法的一种，被广泛认可的MD5算法的继任者。
     * SHA算法家族目前共有SHA-0、SHA-1、SHA-224、SHA-256、SHA-384和SHA-512五种算法， 通常将后四种算法并称为SHA-2算法
     * 
     * @param msg
     * @return
     */
    public static String encryptSHA(String msg)
    {
        String salt = getSaltSHA1();
        StringBuilder sb = new StringBuilder();
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(msg.getBytes());
            for (int i = 0; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("encryptSha error:" + e.toString());
        }
        
        return sb.toString();
    }
    
    /**
     * PBKDF2加密
     * 
     * @param msg
     * @return
     */
    public static String encryptPBKDF2(String msg)
    {
        try
        {
            char[] chars = msg.toCharArray();
            byte[] salt = getSalt();
            PBEKeySpec spec = new PBEKeySpec(chars, salt, ITER_KEY_TIMES, ITER_KEY_LEN);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Hex.toHex(salt) + Hex.toHex(hash);
        }
        catch (Exception e)
        {
            throw new RuntimeException("encryptPBKDF2 error:" + e.toString());
        }
    }
    
    /**
     * PBKDF2加密
     * @param msg
     * @return
     */
    public static String encryptSentiveWithPBK(String msg, byte[] salts)
    {
        try
        {
            char[] chars = msg.toCharArray();
            PBEKeySpec spec = new PBEKeySpec(chars, salts, ITER_KEY_TIMES, ITER_KEY_LEN);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Hex.toHex(hash);
        }
        catch (Exception e)
        {
            throw new RuntimeException("encryptPBKDF2 error:" + e.toString());
        }
    }
    
    /**
     * 使用安全随机数生成
     * 
     * @return
     */
    public static byte[] getSalt()
    {
        SecureRandom sr;
        byte[] salt = new byte[16];
        try
        {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            sr.nextBytes(salt);
        }
        catch (Exception e)
        {
            throw new RuntimeException("getSalt error:" + e.toString());
        }
        return salt;
    }
    
    /**
     * 使用安全随机数生成
     * 
     * @return
     */
    public static String getIv()
    {
        SecureRandom sr;
        byte[] salt = new byte[16];
        try
        {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            sr.nextBytes(salt);
        }
        catch (Exception e)
        {
            throw new RuntimeException("getIv error:" + e.toString());
        }
        return Hex.encodeToString(salt);
    }
    
    /**
     * [list] [*]SHA-1 (Simplest one – 160 bits Hash) [*]SHA-256 (Stronger than SHA-1 – 256 bits Hash) [*]HA-384
     * (Stronger than SHA-256 – 384 bits Hash) [*]SHA-512 (Stronger than SHA-384 – 512 bits Hash) [/list]
     * 
     * @return
     */
    private static String getSaltSHA1()
    {
        SecureRandom sr;
        byte[] salt = new byte[16];
        try
        {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("getSaltSHA1 error:" + e.toString());
        }
        return salt.toString();
    }
    
    /**
     * 基本加密处理
     * 
     * @param msg
     * @param typt
     * @return
     */
    private static String encrypt(String msg, byte[] salts)
    {
        MessageDigest md;
        StringBuilder password = new StringBuilder();
        try
        {
            md = MessageDigest.getInstance("MD5");
            if (salts != null && salts.length > 0)
            {
                md.update(salts);
            }
            else
            {
                md.update(msg.getBytes());
            }
            
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++)
            {
                String param = Integer.toString((bytes[i] & 0xff) + 0x100, 16);
                password.append(param.substring(1));
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("getSaltSHA1 NoSuchAlgorithmException:" + e.toString());
        }
        return password.toString();
    }
}
