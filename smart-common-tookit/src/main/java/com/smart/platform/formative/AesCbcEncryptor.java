/**   
 * @Title: AesCbcEncryptor.java 
 * @Package: com.smart.platform.formative 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年8月9日 下午10:24:13 
 * @version 1.3.1 
 */

package com.smart.platform.formative;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.smart.platform.toolkit.Hex;

/**
 * @Description
 * @author 01135912
 * @date 2016年8月9日 下午10:24:13
 * @version V1.3.1
 */

public class AesCbcEncryptor
{
    private static final String CIPHER_NOPAD_CBC = "AES/CBC/NoPadding";
    private static final String CIPHER_PADDING_CBC = "AES/CBC/PKCS5Padding";
    private  static final String algorithm = "AES";
    
    public static String decryptHex(String pHexText, byte[] keys,String pIv)throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER_NOPAD_CBC);
        SecretKeySpec keySpec = new SecretKeySpec(keys, algorithm);
        byte[] encryptedBytes = Hex.decode(pHexText);
        byte[] iv = createIV(pIv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        System.arraycopy(decryptedBytes, 0, encryptedBytes, 0, encryptedBytes.length);
        String result = new String(encryptedBytes);
        return result.trim();
    }
    
    public static String encryptHex(String pData, byte[] keys, String pIv)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER_NOPAD_CBC);
        byte[] iv = createIV(pIv);
        SecretKeySpec keySpec = new SecretKeySpec(keys, algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] output = cipher.doFinal(paddingData(pData));
        byte[] encryptedContent = new byte[output.length];
        System.arraycopy(output, 0, encryptedContent, 0, encryptedContent.length);
        String result = new String(Hex.encode(encryptedContent)).toUpperCase();
        return result;
    }
    
    public static String decryptBase64(String encodeStr, byte[] keys)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER_PADDING_CBC);
        SecretKeySpec keySpec = new SecretKeySpec(keys, algorithm);
        byte[] finalBytes = Base64.decodeBase64(encodeStr);
        byte[] iv = Arrays.copyOfRange(finalBytes, 0, SercurityHelper.SALT_LEN);
        byte[] encodeBytes = Arrays.copyOfRange(finalBytes, SercurityHelper.SALT_LEN, finalBytes.length);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(encodeBytes);
        if(decryptedBytes != null && decryptedBytes.length > 0)
        {
            return new String(decryptedBytes,"UTF-8").trim();
        }
        return null;
    }
    
    public static String encryptBase64(String inputData, byte[] keys)
        throws Exception
    {
        if(inputData == null || inputData.isEmpty())
        {
            return null;
        }
        Cipher cipher = Cipher.getInstance(CIPHER_PADDING_CBC);
        SecretKeySpec keySpec = new SecretKeySpec(keys, algorithm);
        byte[] iv = SercurityHelper.getIv();
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] output = cipher.doFinal(inputData.trim().getBytes("UTF-8"));
        byte[] finalBytes = new byte[output.length + iv.length];
        System.arraycopy(iv, 0, finalBytes, 0, iv.length);
        System.arraycopy(output, 0, finalBytes, iv.length, output.length);
        return Base64.encodeBase64String(finalBytes);
    }
    
    /**
     * 
     * 补齐的16位的整数倍
     * @param pData
     * @return
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
     * 初始化向量到16位
     * */
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