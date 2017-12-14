/**
 * Project Name:basicplatform
 * File Name:FormativeDataTest.java
 * Package Name:com.smart.platform.formative
 * Date:2016年8月10日下午1:42:09
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.formative;

import java.io.UnsupportedEncodingException;

import org.dom4j.util.UserDataElement;
import org.junit.Test;

import com.smart.platform.encrypt.CollecterEncrypt;
import com.smart.platform.encrypt.SystemConfigEncrypt;
import com.smart.platform.encrypt.UserDataEncrypt;

/**
 * ClassName:FormativeDataTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月10日 下午1:42:09 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class FormativeDataTest
{
    @Test
    public void generateKey()
    {
        long str = 1476931601029L;
        for(int i=0;i<2;i++)
        {
            String key1 = "1003001UAAM-ADMIN-WEBAPP"+String.valueOf(str);
            String passwordKey1 = HashSensitiveEncyptor.encryptPBKDF2(key1);
            System.out.println(passwordKey1);
            
            String key2 = "1003001UAAM-ADMIN-WEBAPP"+String.valueOf(str);
            String passwordKey2 = HashSensitiveEncyptor.encryptSHA(key2);
            System.out.println(passwordKey2);
            
            String key3 = "1003001UAAM-ADMIN-WEBAPP";
            String passwordKey3 = HashSensitiveEncyptor.encryptMD5(key3);
            System.out.println(passwordKey3);
        }
    }

    @Test
    public void generateUserSaltTest()
    {
        System.out.println(SercurityHelper.getSalt());
    }
    
    @Test
    public void generateAppKeyTest()
    {
        String salt = "3df3f1a29c83cc112127af514d912031";
        String timestamp = "1476931601029";
        String appendData = "1001001ISIC-SOP-SOC"+timestamp;
        System.out.println(HashSensitiveEncyptor.HMACSHA256(appendData.getBytes(), salt.getBytes()));
    }
    
    @Test
    public void encodePassword() throws Exception
    {
        String salt = SercurityHelper.getSalt();
        
        System.out.println("Salt:"+salt);
        String KEYAPP =
            "1d13caf2f49d0c2614e6529a963032b99b82c36a7f2cf353495582ea1bca90714428670c8cc7bbe8d55d2e6ef69dfccb";
        String ENCODE_COMPONENT_APP = "5e3af2b4ab8fe2b1dd8233bc9991e7568093208310468ea9df1606842262e3212d071ddd75a57a48a513092a997ec7c3";
        
        String inputPassword = "Sf6rwhkjz!";
        byte[] workKey = SercurityHelper.getWorkSecretKey(KEYAPP,ENCODE_COMPONENT_APP,salt);
        String passwordDecode = AesCbcEncryptor.encryptBase64(inputPassword,workKey);
        System.out.println("encode password:"+passwordDecode);
    }
    
    @Test
    public void decodePassword() throws Exception
    {
        String configValue = "sfs@f1admin";
        String salt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        String KEYAPP =
            "b2fc6fe5343eabca073ae2f54cd9a046dc312c937b990de1e89b543220c3c04c339cb00f647159f5b2bd94da2f890561";
        String ENCODE_COMPONENT_APP = "a1a998c79e010eeb17e2162e5a7d19201841177612987a6dce1763c78dd31dcc7bc2474faf3c2eed15f5a7616c3f0043";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(KEYAPP, ENCODE_COMPONENT_APP, salt);
        System.out.println(HashSensitiveEncyptor.encryptSentiveWithPBK(configValue, workKey));
    }
    
    @Test
    public void decodeUserPassword()
    {
        System.out.println(CollecterEncrypt.decodeUserData("rqbsoLl9rM5fVKISWbkR8zaIAhpkzHxS9B5gxL9Bcbo=","e30430c9e289ee6363e8a56a26cbf9f8"));
    }
    
    @Test
    public void endcodeUserPassword()
    {
        System.out.println(CollecterEncrypt.encodeUserData("jnqn3@vyw","bb1fa69c911454e0f01cc7574487de80"));
    }
    
    @Test
    public void decodeUserPassword256()
    {
        System.out.println(CollecterEncrypt.decodeUserData256("rqbsoLl9rM5fVKISWbkR8zaIAhpkzHxS9B5gxL9Bcbo=","e30430c9e289ee6363e8a56a26cbf9f8"));
    }
    
    @Test
    public void endcodeUserPassword256()
    { 
        String keyStr = "123";
        System.out.println(keyStr.getBytes());
        System.out.println(CollecterEncrypt.encodeUserData256("123","bb1fa69c911454e0f01cc7574487de80"));
    }
    
    /**soc operater***/
    @Test
    public void encodeSysOperateConfig() throws Exception{
        String configValue = "passwd123";
        
        String fileComponent = "015aa74b43084588ddf4db96e58ee8973a6149007302d2d080272bff6f4f8d18281fd334280e90de236986fc63596b4b";
        String componentApp =
            "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
        String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
        String encodeData = AesCbcEncryptor.encryptBase64(configValue, workKey);
        System.out.println("encode data is:"+encodeData);
        String decodeData = SystemConfigEncrypt.decryptConfig(fileComponent, encodeData);
        System.out.println("decode data is:"+decodeData);
    }
    
    /**soc operater***/
    @Test
    public void encde_user_Operate_config() throws Exception{
        String configValue = "passwd123";
        
        String fileComponent = "29921b3570c37afb1e0566e3ab4c7d95eb4fb30fb92bb40f2d95391461e7fa00477f2676b68e3cda8ea144f569f7e308";
        String componentApp =
            "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
        String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
        String encodeData = AesCbcEncryptor.encryptBase64(configValue, workKey);
        System.out.println("encode data is:"+encodeData);
        String decodeData = SystemConfigEncrypt.decryptConfig(fileComponent, encodeData);
        System.out.println("decode data is:"+decodeData);
    }
    
    /**SOC PORTAL***/
    @Test
    public void encodeSystemSocConfig() throws Exception{
        String configValue = "passwd123";
        
        String fileComponent = "29921b3570c37afb1e0566e3ab4c7d95eb4fb30fb92bb40f2d95391461e7fa00477f2676b68e3cda8ea144f569f7e308";
        String componentApp =
            "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
        String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
        String encodeData = AesCbcEncryptor.encryptBase64(configValue, workKey);
        System.out.println("encode data is:"+encodeData);
        String decodeData = SystemConfigEncrypt.decryptConfig(fileComponent, encodeData);
        System.out.println("decode data is:"+decodeData);
    }
    
    /**SRC PORTAL***/
    @Test
    public void encodeSystemConfig() throws Exception{
        String fileComponent = "b2fc6fe5343eabca073ae2f54cd9a046dc312c937b990de1e89b543220c3c04c339cb00f647159f5b2bd94da2f890561";
        String configValue = "sfs@f1admin";
        
        String componentApp =
            "a1a998c79e010eeb17e2162e5a7d19201841177612987a6dce1763c78dd31dcc7bc2474faf3c2eed15f5a7616c3f0043";
        String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
        String encodeData = AesCbcEncryptor.encryptBase64(configValue, workKey);
        System.out.println("encode data is:"+encodeData);
        String decodeData = SystemConfigEncrypt.decryptConfig(fileComponent, encodeData);
        System.out.println("decode data is:"+decodeData);
    }
    
    /**SRC admin***/
    @Test
    public void encodeAdminSystemConfig() throws Exception{
        String fileComponent = "616e7fbd17e8267d8ad3fabb6c32140d83c9a674d3da0e68b7e3d2e2eca6c63d5fea19cd28dd3b76b4b73980f3aec7cc";
        String configValue = "34b75e4c16eb46e0a4102693d190b5c9";
        
        String componentApp =
            "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
        String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
        
        byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
        String encodeData = AesCbcEncryptor.encryptBase64(configValue, workKey);
        System.out.println("encode data is:"+encodeData);
        String decodeData = SystemConfigEncrypt.decryptConfig(fileComponent, encodeData);
        System.out.println("decode data is:"+decodeData);
    }
    
    
    @Test
    public void encodeUserName(){
        for(int i=0;i<100;i++){
            String userSalt = "0d5580daad5f12de809e3571685aa810";
            System.out.println(HashSensitiveEncyptor.HMACSHA256Base64("from archives.models import LEVEL_STATUS_CHOICES", userSalt));
        }
    }
    
    @Test
    public void encodeUserPassword() throws UnsupportedEncodingException{
        for(int i=0;i<10;i++)
        {
            String fileComponent = "1853ffa66d1b9f08e5da6d0cfd2a9297a652714ce816ee566bc660f5f349a337911a8fa7775165c4ee92cb877a57aca0";
            String configValue = "sfs@f123456";
            
            String componentApp =
                "556522e59f4545fbaa2a12ca72209e5e7a407d24094531d27f12c0cde7c14a7b63a23f1ff6aa0dc9f35686e47b2de125";
            String configSalt = "ae6d6366bd97c5d9aeade3f6f99a5a85";
            
            byte[] workKey = SercurityHelper.getWorkSecretKey(fileComponent, componentApp, configSalt);
            String passStr =  HashSensitiveEncyptor.encryptSentiveWithPBK(i+"", workKey);
            System.out.println(passStr);
        }
    }
}

