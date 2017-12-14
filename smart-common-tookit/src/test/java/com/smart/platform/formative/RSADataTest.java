/**
 * Project Name:basicplatform
 * File Name:FormativeDataTest.java
 * Package Name:com.smart.platform.formative
 * Date:2016年8月10日下午1:42:09
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.formative;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * ClassName:FormativeDataTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年8月10日 下午1:42:09 <br/>
 * 
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public class RSADataTest
{
    @Test
    public void rsaDataTest()
        throws Exception
    {
        RSACryptor rsa = new RSACryptor();
        // rsa.createKey();
        RSAPublicKey publicKey = (RSAPublicKey)loadKey("E://public_key");
        String endata = rsa.encrypttoStr(publicKey, "12345");
        RSAPrivateKey privateKey = (RSAPrivateKey)loadKey("E://private_key");
        String data = rsa.decrypttoStr(privateKey, endata);
        
        // 模
        String Modulus = publicKey.getModulus().toString(16);
        // 公钥指数
        String Exponent = publicKey.getPublicExponent().toString(16);
        // 私钥指数
        String private_exponent = privateKey.getPrivateExponent().toString();
        System.out.println("Exponent:" + Exponent);
        System.out.println("Modulus:" + Modulus);
        // 使用模和指数获取公钥
        RSAPublicKey pukey = RSACryptor.getPublicKey(publicKey.getModulus().toString(), publicKey.getPublicExponent().toString());
        RSAPrivateKey prkey = RSACryptor.getPrivateKey(publicKey.getModulus().toString(), private_exponent);
        String jspwd =
            "376e08b67b5553bd738b2a8e45c9074c1296d46a3bb85345666f73bbfefcf7467d6272db9e3194bd47cd1fce63c69e03c705aae23caf01f4f3ffbe23ec111fc6";
        
        // String data_16 = rand.parseByte2HexStr(jspwd.getBytes());
        System.out.println("js解密长度：" + jspwd.length());
        String data2 = rsa.decrypttoStr(privateKey, jspwd);
        System.out.println("js解密：" + data2);
        System.out.println("加密后内容：" + new String(endata));
        System.out.println("解密后内容：" + new String(data));
    }
    
    /**
     * 从硬盘中加载私钥
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public Key loadKey(String keyUrl)
        throws IOException, FileNotFoundException, ClassNotFoundException
    {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(keyUrl)));
        Key key = (Key)inputStream.readObject();
        return key;
    }
    
    /**
     * 把私钥或则公钥保存到硬盘上
     * 
     * @param privateKey
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void saveKey(Key key, String saveUrl)
        throws IOException, FileNotFoundException
    {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(saveUrl)));
        outputStream.writeObject(key);
    }
}
