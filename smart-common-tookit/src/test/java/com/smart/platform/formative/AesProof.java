/**
 * Project Name:basicplatform
 * File Name:AesProof.java
 * Package Name:com.smart.platform.formative
 * Date:2016年8月22日上午10:51:34
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.formative;

import java.io.UnsupportedEncodingException;

/**
 * ClassName:AesProof <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月22日 上午10:51:34 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.smart.platform.toolkit.Hex;

/**
 * java Aes256 加密
 * 
 * @author jlins
 */

public class AesProof
{
    
    // 说明 key 需要大家自己去设定加密解密的key，key牵涉到安全信息,所以这里无法公布
    
    private static final byte[] key = "test1111111111111111test".getBytes();
    
    private static final String transform = "AES/CBC/NoPadding";
    
    private static final String algorithm = "AES";
    
    private static final SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
    
    public static void main(String[] args)
        throws Exception
    {
        for(int i=0;i<key.length;i++)
        {
            System.out.println(key[i]);
        }
        String pwds[] = {"123", "0123456789012345", "01234567890123456", "123", "123", "0123456789012345678",
            "012345678901234567890123456789", "b", "0123456789012345", "01234567890123456", "012345678901234567"};
        String ivss[] = {"test", "test", "test", "test0123456789012", "test01234567890123", "test", "test", "a", "test",
            "test", "test"};
        String rr[] = new String[ivss.length];
        for (int i = 0; i < ivss.length; i++)
        {
            String en = encrypt(pwds[i], ivss[i]);
            String decy = decrypt(en, ivss[i]);
            rr[i] = "[" + ivss[i] + "],[" + decy + "]-->[" + en + "]";
            System.out.println(rr[i]);
        }
        System.out.println("---------");
        for (int i = 0; i < rr.length; i++)
        {
            System.out.println(rr[i]);
        }
    }
    
    /**
     
    */
    
    public static String decrypt(String pHexText, String pIv)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(transform);
        byte[] encryptedBytes = Hex.decode(pHexText);
        byte[] iv = createIV(pIv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        System.arraycopy(decryptedBytes, 0, encryptedBytes, 0, encryptedBytes.length);
        String result = new String(encryptedBytes);
        return result.trim();
    }
    
    /**
     
    */
    
    public static String encrypt(String pData, String pIv)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(transform);
        byte[] iv = createIV(pIv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] output = cipher.doFinal(paddingData(pData));
        byte[] encryptedContent = new byte[output.length];
        System.arraycopy(output, 0, encryptedContent, 0, encryptedContent.length);
        String result = new String(Hex.encode(encryptedContent)).toUpperCase();
        return result;
    }
    
    /**
     * 
     * 补齐的16位的整数倍
     *
     * 
     * 
     * @param pData
     * 
     * @return
     * 
     */
    
    private static byte[] paddingData(String pData)
    {
        byte[] bytes = pData.getBytes();
        int length = bytes.length / 16;
        if (length * 16 < bytes.length)
        {
            length++;
        }
        byte[] result = new byte[length * 16];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        for (int i = bytes.length; i < result.length; i++)
        {
            result[i] = 0x00;
        }
        return result;
    }
    
    /**
     * 
     * 初始化向量到16位
     * 
     */
    
    private static byte[] createIV(String pIv)
        throws UnsupportedEncodingException
    {
        byte[] bytes = pIv.getBytes("US-ASCII");
        int length = bytes.length / 16;
        if (length * 16 < bytes.length)
        {
            length++;
        }
        byte[] result = new byte[16];
        System.arraycopy(bytes, 0, result, 0, bytes.length > 16 ? 16 : bytes.length);
        for (int i = bytes.length; i < result.length; i++)
        {
            result[i] = 0x00;
        }
        return result;
    }
    
}