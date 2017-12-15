package com.smart.platform.formative;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.platform.constant.HttpConstant;

/**
 * ClassName: RSACrypto <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月27日 下午2:29:05 <br/>
 *
 * @author 01135912
 * @version
 * @since JDK 1.7
 */
public class RSACryptor
{
    public static final int KEYSIZE = 512;
    
    private static final String ENCRYPT_METHOD = "RSA";
    
    private static final Logger LOG = LoggerFactory.getLogger(RSACryptor.class);
    
    /**
     * 加密
     * 
     * @param publicKey 公钥
     * @param content 需要加密的内容
     * @return
     * @throws Exception
     */
    public static String encrypttoStr(Key publicKey, String content)
        throws Exception
    {
        return parseByte2HexStr(publicEnrypy(publicKey, content));
    }
    
    /**
     * 解密
     * 
     * @param privateKey 私钥
     * @param endata 需要解密的内容
     * @return
     * @throws Exception
     */
    public static String decrypttoStr(Key privateKey, String endata)
        throws Exception
    {
        return new String(privateEncode(privateKey, parseHexStr2Byte(endata)));
    }
    
    public String decrypttoStr_normal(Key privateKey, String endata)
        throws Exception
    {
        String data = new String(privateEncode(privateKey, endata.getBytes()));
        return data;
    }
    
    /**
     * 加密的方法,使用公钥进行加密
     * 
     * @param publicKey 公钥
     * @param data 需要加密的数据
     * @throws Exception
     */
    public static byte[] publicEnrypy(Key publicKey, String data)
        throws Exception
    {
        
        Cipher cipher = Cipher.getInstance(ENCRYPT_METHOD, new BouncyCastleProvider());
        
        // 设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        // 对数据进行加密
        byte[] result = cipher.doFinal(data.getBytes());
        
        return result;
    }
    
    /**
     * 解密的方法，使用私钥进行解密 privateKey 私钥 encoData 需要解密的数据
     * 
     * @throws Exception
     */
    public static byte[] privateEncode(Key privateKey, byte[] encoData)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(ENCRYPT_METHOD, new BouncyCastleProvider());
        
        // 设置为解密模式，用私钥解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 解密
        byte[] data = cipher.doFinal(encoData);
        // System.out.println("解密后的数据："+data);
        return data;
    }
    
    /**
     * 自动生成密钥对
     * 
     * @throws Exception
     */
    public static Map<String, Object> createKey()
    {
        try
        {
            // Cipher cipher = Cipher.getInstance(ENCRYPT_METHOD);
            KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance(ENCRYPT_METHOD, new BouncyCastleProvider());
            
            SecureRandom random = new SecureRandom();
            keyPairGenerator.initialize(RSACryptor.KEYSIZE, random);
            // 生成钥匙对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(HttpConstant.RSA_PUBLIC_KEY, publicKey);
            map.put(HttpConstant.RSA_PRIVATE_KEY, privateKey);
            
            return map;
        }
        catch (Exception e)
        {
            LOG.error("generate rsa key error, {}", e.toString());
            return null;
        }
    }
    
    /**
     * 使用模和指数生成RSA公钥
     * 
     * 
     * @param modulus 模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent)
    {
        try
        {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory =
                KeyFactory.getInstance(ENCRYPT_METHOD, new BouncyCastleProvider());
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey)keyFactory.generatePublic(keySpec);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 使用模和指数生成RSA私钥 /None/NoPadding】
     * 
     * @param modulus 模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent)
    {
        try
        {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_METHOD, new BouncyCastleProvider());
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public String randKey()
    {
        SecureRandom r = new SecureRandom();
        String code = "";
        
        for (int i = 0; i < 9; ++i)
        {
            if (i % 2 == 0) // 偶数位生产随机整数
            {
                code = code + r.nextInt(10);
            }
            else// 奇数产生随机字母包括大小写
            {
                int temp = r.nextInt(52);
                char x = (char)(temp < 26 ? temp + 97 : (temp % 26) + 65);
                code += x;
            }
        }
        
        System.out.println(code);
        return code;
    }
    
    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return String
     */
    public static String parseByte2HexStr(byte buf[])
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1)
            {
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
     * @return byte[]
     */
    public static byte[] parseHexStr2Byte(String hexStr)
    {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }
}