/**
 * Project Name:iatp-basic-common
 * File Name:UserBusiness.java
 * Package Name:com.smart.platform.business
 * Date:2017年2月28日下午12:02:34
 * Copyright (c) 2017, xutao9@sf-express.com All Rights Reserved.
 *
*/

package com.smart.platform.encrypt;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.smart.platform.formative.AesCbcEncryptor;
import com.smart.platform.formative.HashSensitiveEncyptor;
import com.smart.platform.formative.SercurityHelper;
import com.smart.platform.toolkit.PropertyPlaceholderConfigurer;

/**
 * ClassName: UserBusiness <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年2月28日 下午12:02:34 <br/>
 *
 * @author 01135912
 * @version
 * @since JDK 1.7
 */
public class UserDataEncrypt
{
    public final static String ENCODE_COMPONENT_APP =
        "a1a998c79e010eeb17e2162e5a7d19201841177612987a6dce1763c78dd31dcc7bc2474faf3c2eed15f5a7616c3f0043";
    
    public final static String USER_DEFAULT_SALT = "ae6d6366bd97c5d9aeade3f6f99a5a85";
    
    private static final String USER_COMPONENT_KEY = "user.key.component";
    
    private static String USER_COMPONENT_VALUE = null;
    
    private static final Logger LOG = LoggerFactory.getLogger(UserDataEncrypt.class);
    
    private static final String KEY_START = "#c";
    
    private static UserDataEncrypt instance;
    
    public static synchronized UserDataEncrypt getUserEncrypter()
    {
        if (instance == null)
        {
            instance = new UserDataEncrypt();
        }
        
        if (USER_COMPONENT_VALUE == null || USER_COMPONENT_VALUE.isEmpty())
        {
            USER_COMPONENT_VALUE = PropertyPlaceholderConfigurer.getConfigValue(USER_COMPONENT_KEY);
        }
        return instance;
    }
    
    /**
     * decryptConfig:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author 01135912
     * @param userSalt
     * @param configValue
     * @return
     * @since JDK 1.6
     */
    public String decryptData(String userSalt, String encodeData)
    {
        String decodeData = null;
        try
        {
            if (encodeData == null || !encodeData.startsWith(KEY_START))
            {
                return null;
            }
            encodeData = encodeData.substring(KEY_START.length());
            byte[] workKey = SercurityHelper.getWorkSecretKey(USER_COMPONENT_VALUE, ENCODE_COMPONENT_APP, userSalt);
            decodeData = AesCbcEncryptor.decryptBase64(encodeData, workKey);
        }
        catch (Exception e)
        {
            LOG.error("decryt config value error {}", e);
        }
        return decodeData;
    }
    
    /**
     * encryptConfig:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author 01135912
     * @param userSalt
     * @param configValue
     * @return
     * @since JDK 1.6
     */
    public String encryptData(String userSalt, String inputData)
    {
        String decodeData = null;
        try
        {
            byte[] workKey = SercurityHelper.getWorkSecretKey(USER_COMPONENT_VALUE, ENCODE_COMPONENT_APP, userSalt);
            decodeData = KEY_START + AesCbcEncryptor.encryptBase64(inputData, workKey);
        }
        catch (Exception e)
        {
            LOG.error("decryt config value error {}", e);
        }
        return decodeData;
    }
    
    public <T>T decryptObj(String userSalt, String inputData, Class<T> clazz)
    {
        try
        {
            String decryptData = decryptData(userSalt, inputData);
            return JSON.parseObject(decryptData, clazz);
        }
        catch (Exception e)
        {
            LOG.error("decryt config value error {}", e);
            return null;
        }
    }
    
    public String hashUserInfo(String inputUserName)
    {
        return HashSensitiveEncyptor.HMACSHA256Base64(inputUserName, USER_DEFAULT_SALT);
    }

    public String encryptSensitive(String password, String userSalt) throws UnsupportedEncodingException
    {
        byte[] workKey = SercurityHelper.getWorkSecretKey(USER_COMPONENT_VALUE, ENCODE_COMPONENT_APP, userSalt);
        return HashSensitiveEncyptor.encryptSentiveWithPBK(password, workKey);
    }
}
