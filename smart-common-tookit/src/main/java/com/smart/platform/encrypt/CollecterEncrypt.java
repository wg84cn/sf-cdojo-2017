package com.smart.platform.encrypt;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.platform.formative.AesCbcEncryptor;
import com.smart.platform.formative.SercurityHelper;

public final class CollecterEncrypt
{
	private CollecterEncrypt(){
		
	}
	
    public final static String ENCODE_COMPONENT_APP =
        "5e3af2b4ab8fe2b1dd8233bc9991e7568093208310468ea9df1606842262e3212d071ddd75a57a48a513092a997ec7c3";
    
    public final static String ENCODE_FILE_COMPONENT =
        "1d13caf2f49d0c2614e6529a963032b99b82c36a7f2cf353495582ea1bca90714428670c8cc7bbe8d55d2e6ef69dfccb";
    
    private static final String dataSalt = "bb1fa69c911454e0f01cc7574487de80";
    
    private static final String keyStart = "#c";
    
    private static byte[] workKey = null;
    
    private static final Logger LOG = LoggerFactory.getLogger(CollecterEncrypt.class);
    
    static
    {
        try
        {
            workKey = SercurityHelper.getWorkSecretKey(ENCODE_FILE_COMPONENT, ENCODE_COMPONENT_APP, dataSalt);
        }
        catch (UnsupportedEncodingException e)
        {
        	LOG.error("UnsupportedEncodingException {}", e);
            throw new IllegalArgumentException("The work key can not init,please recheck it!");
        }
    }
    
    public static final String decodeData(String data)
    {
        if (data == null || !data.startsWith(keyStart))
        {
            return data;
        }
        
        String decodeData = null;
        try
        {
            decodeData = AesCbcEncryptor.decryptBase64(data.substring(keyStart.length()), workKey);
        }
        catch (Exception e)
        {
            LOG.error("Data decode error  {}", e);
        }
        return decodeData;
    }
    
    public static final String encodeData(String data)
    {
        if (data == null || data.startsWith(keyStart))
        {
            return data;
        }
        
        String encodeData = null;
        try
        {
            
            encodeData = keyStart + AesCbcEncryptor.encryptBase64(data, workKey);
        }
        catch (Exception e)
        {
            LOG.error("Data encode error {}", e);
        }
        return encodeData;
    }
    
    public static final String decodeUserData(String data,String dataSalt)
    {
        String decodeData = null;
        try
        {
            byte[] dataWorkKey = 
                SercurityHelper.getWorkSecretKey(ENCODE_FILE_COMPONENT, ENCODE_COMPONENT_APP, dataSalt);
            decodeData = AesCbcEncryptor.decryptBase64(data, dataWorkKey);
        }
        catch (Exception e)
        {
            LOG.error("Data decode error:{}", e);
        }
        return decodeData;
    }
    
    public static final String encodeUserData(String data,String dataSalt)
    {
        String encodeData = null;
        try
        {
            byte[] dataWorkKey = 
                SercurityHelper.getWorkSecretKey(ENCODE_FILE_COMPONENT, ENCODE_COMPONENT_APP, dataSalt);
            encodeData = AesCbcEncryptor.encryptBase64(data, dataWorkKey);
        }
        catch (Exception e)
        {
            LOG.error("Data encode error:{}", e);
        }
        return encodeData;
    }
    
    public static final String decodeUserData256(String data,String dataSalt)
    {
        String decodeData = null;
        try
        {
            byte[] dataWorkKey = 
                SercurityHelper.getWorkSecretKey256(ENCODE_FILE_COMPONENT, ENCODE_COMPONENT_APP, dataSalt);
            decodeData = AesCbcEncryptor.decryptBase64(data, dataWorkKey);
        }
        catch (Exception e)
        {
            LOG.error("Data decode error:{}", e);
        }
        return decodeData;
    }
    
    public static final String encodeUserData256(String data,String dataSalt)
    {
        String encodeData = null;
        try
        {
            byte[] dataWorkKey = 
                SercurityHelper.getWorkSecretKey256(ENCODE_FILE_COMPONENT, ENCODE_COMPONENT_APP, dataSalt);
            encodeData = AesCbcEncryptor.encryptBase64(data, dataWorkKey);
        }
        catch (Exception e)
        {
            LOG.error("Data encode error:{}", e);
        }
        return encodeData;
    }
}