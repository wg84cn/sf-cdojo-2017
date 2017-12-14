/**
 * Project Name:iatp-basic-common
 * File Name:ConfigProcess.java
 * Package Name:com.smart.platform.business
 * Date:2017年2月28日上午11:13:25
 * Copyright (c) 2017, xutao9@sf-express.com All Rights Reserved.
 *
*/

package com.smart.platform.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.platform.formative.AesCbcEncryptor;
import com.smart.platform.formative.SercurityHelper;

/**
 * ClassName:ConfigProcess <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年2月28日 上午11:13:25 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * ClassName: ConfigProcess <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年2月28日 上午11:13:25 <br/>
 *
 * @author 01135912
 * @version 
 * @since JDK 1.7
 */
public final class SystemConfigEncrypt
{
    private final static String ENCODE_COMPONENT_APP =
        "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
    
    private static final String DEFAULT_CONFIG_SALT = "ae6d6366bd97c5d9aeade3f6f99a5a85";
    
    private static byte[] workKey = null;
    
    private static final Logger LOG = LoggerFactory.getLogger(SystemConfigEncrypt.class);
    
    public static final String decryptConfig(String fileComponent, String configValue)
    {
        String decodeData = null;
        try
        {
            if (workKey == null || workKey.length < 0)
            {
                workKey = SercurityHelper.getWorkSecretKey(fileComponent, ENCODE_COMPONENT_APP, DEFAULT_CONFIG_SALT);
            }
            decodeData = AesCbcEncryptor.decryptBase64(configValue, workKey);
        }
        catch (Exception e)
        {
            LOG.error("decryt config value error {}", e.toString());
        }
        return decodeData;
    }
}

