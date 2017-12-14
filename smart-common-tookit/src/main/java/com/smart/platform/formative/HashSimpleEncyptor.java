/**   
 * @Title: HashEncyptor.java 
 * @Package: com.smart.platform.formative 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年8月9日 下午11:40:39 
 * @version 1.3.1 
 */

package com.smart.platform.formative;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @Description
 * @author Administrator
 * @date 2016年8月9日 下午11:40:39
 * @version V1.3.1
 */

public class HashSimpleEncyptor
{
    /**
     * SHA（Secure Hash Algorithm，安全散列算法）是消息摘要算法的一种，被广泛认可的MD5算法的继任者。
     * SHA算法家族目前共有SHA-0、SHA-1、SHA-224、SHA-256、SHA-384和SHA-512五种算法， 通常将后四种算法并称为SHA-2算法
     */
    public final static String MD5 = "MD5";
    public final static String NONE = "NONE";
    public final static String SHA_256 = "SHA-256";
    public final static String SHA_512 = "SHA-512";
    public final static String SHA_384 = "SHA-384";
    
    /**
     * 加密密码算法
     * 
     * @param pass
     *            需要加密的原始密码
     * @param algorithm
     *            加密算法名称
     * @return 加密后的密码
     * @throws NoSuchAlgorithmException
     *             当加密算法不可用时抛出此异常
     */
    public static String digestString(String inputText, String alg) throws NoSuchAlgorithmException {
        String newPass;
        if (alg == null || MD5.equals(alg)) {
            newPass = DigestUtils.md5Hex(inputText);
        } else if (NONE.equals(alg)) {
            newPass = inputText;
        } else if (SHA_256.equals(alg)) {
            newPass = DigestUtils.sha256Hex(inputText);
        } else if (SHA_384.equals(alg)) {
            newPass = DigestUtils.sha384Hex(inputText);
        } else if (SHA_512.equals(alg)) {
            newPass = DigestUtils.sha512Hex(inputText);
        } else {
            newPass = DigestUtils.sha1Hex(inputText);
        }
        return newPass;
    }

    /**
     * 加密密码算法，默认的加密算法是MD5
     * 
     * @param password
     *            未加密的密码
     * @return String 加密后的密码
     */
    public static String digestPassword(String password) {
        try {
            if (password != null && !"".equals(password)) {
                return digestString(password, MD5);
            } else
                return null;
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Security error: " + nsae);
        }
    }

    /**
     * 判断密码是不是相等，默认的加密算法是MD5
     * 
     * @param beforePwd
     *            要判断的密码
     * @param afterPwd
     *            加密后的数据库密码
     * @return Boolean true 密码相等
     */
    public static boolean isPasswordEnable(String beforePwd, String afterPwd) {
        if (beforePwd != null && !"".equals(beforePwd)) {
            String password = digestPassword(beforePwd);
            return afterPwd.equals(password);
        } else
            return false;
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
        String salt = SercurityHelper.getSalt();
        return encrypt(msg, salt);
    }
    
    /**
     * 基本加密处理
     * 
     * @param msg
     * @param typt
     * @return
     */
    private static String encrypt(String msg, String type)
    {
        MessageDigest md;
        StringBuilder password = new StringBuilder();
        try
        {
            md = MessageDigest.getInstance("MD5");
            if (!StringUtils.isEmpty(type))
            {
                md.update(type.getBytes());
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
            throw new RuntimeException("encrypt NoSuchAlgorithmException:" + e.toString());
        }
        return password.toString();
    }
}
