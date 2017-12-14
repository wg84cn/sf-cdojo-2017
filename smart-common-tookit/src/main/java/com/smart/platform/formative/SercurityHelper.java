
/**   
 * @Title: SercurityHelper.java 
 * @Package: com.smart.platform.formative 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年8月10日 上午12:24:24 
 * @version 1.3.1 
 */

package com.smart.platform.formative;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import com.smart.platform.toolkit.Hex;

/**
 * @Description
 * @author Administrator
 * @date 2016年8月10日 上午12:24:24
 * @version V1.3.1
 */

public final class SercurityHelper
{
    final static String CODE_COMPONENT = "";
    
    public static final int PWD_KEY_TIMES = 10240;
    
    public static final int SALT_LEN = 16;
    
    private static final int KEY_LEN = 16;
    
    private static final int KEY_LEN_256 = 32;
    
    public static byte[] getWorkSecretKey(String componentX, String componentY, String inputSalt)
        throws UnsupportedEncodingException
    {
        BigInteger bigx = new BigInteger(componentX, 16);
        BigInteger bigy = new BigInteger(componentY, 16);
        String inputContext = bigx.xor(bigy).toString(16);
        byte[] outKey = HashSensitiveEncyptor.HMACSHA256(inputContext.getBytes(), inputSalt.getBytes());
        return Arrays.copyOf(outKey, KEY_LEN);
    }
    
    public static byte[] getWorkSecretKey256(String componentX, String componentY, String inputSalt)
        throws UnsupportedEncodingException
    {
        BigInteger bigx = new BigInteger(componentX, 16);
        BigInteger bigy = new BigInteger(componentY, 16);
        String inputContext = bigx.xor(bigy).toString(16);
        byte[] outKey = HashSensitiveEncyptor.HMACSHA256(inputContext.getBytes(), inputSalt.getBytes());
        return Arrays.copyOf(outKey, KEY_LEN_256);
    }
    
    /**
     * 使用安全随机数生成
     * 
     * @return
     */
    public static byte[] getIv()
    {
        byte[] secureBytes = new byte[SALT_LEN];
        try
        {
            SecureRandom secure = SecureRandom.getInstance("SHA1PRNG", "SUN");
            secure.nextBytes(secureBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("encrypt Exception:" + e.toString());
        }
        return secureBytes;
    }
    
    /**
     * 使用安全随机数生成
     * 
     * @return
     */
    public static String getSalt()
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
            throw new RuntimeException("encrypt Exception:" + e.toString());
        }
        return Hex.toHex(salt);
    }
}
