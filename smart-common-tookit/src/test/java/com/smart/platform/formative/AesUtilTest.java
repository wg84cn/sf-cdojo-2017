/**
 * Project Name:basicplatform
 * File Name:AesUtil.java
 * Package Name:com.smart.platform.formative
 * Date:2016年8月22日上午10:30:47
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.formative;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;

/**
 * ClassName:AesUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月22日 上午10:30:47 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class AesUtilTest
{/*
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AesUtilTest.class);

    public static void main(String args[]) throws Exception {
        String data = "h4oR49hsSvXdAiuAl3w7WX0izdTSbb5g";
        String key = getCredential();
        System.out.println(key);
        String encryptStr = encrypt(data, "deploy", key);
        System.out.println(encryptStr);
        System.out.println(decrypt(encryptStr, "deploy", key));

        // System.out.println(Toolkit.getFileMD5("C:\\data\\conf\\passwd\\credential.md5"));
    }

    public static String getCredentialPwd() {
        String _sp = Config.SYSCONF_PATH + Config.SEPARATOR + Config.CONF_DEPLOY_PWD_S = null;
        String _ep = Config.SYSCONF_PATH + Config.SEPARATOR + Config.CONF_DEPLOY_PWD_E = null;

        File startF = new File(_sp);
        File endF = new File(_ep);
        byte[] startBuf = new byte[(int) startF.length()];
        byte[] endBuf = new byte[(int) endF.length()];
        FileInputStream startFis = null;
        FileInputStream endFis = null;
        String _s = null, _e = null;
        try {
            if (startF.exists()) {
                startFis = new FileInputStream(startF);
                startFis.read(startBuf);
                _s = new String(startBuf, "ISO-8859-1");
            } else {
                logger.error("deploy.pwd.s文件不存在!");
                throw new RuntimeException("deploy.pwd.s文件不存在!");
            }
            if (endF.exists()) {
                endFis = new FileInputStream(endF);
                endFis.read(endBuf);
                _e = new String(endBuf, "ISO-8859-1");
            } else {
                logger.error("deploy.pwd.e文件不存在!");
                throw new RuntimeException("deploy.pwd.e文件不存在!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (startFis != null)
                    startFis.close();
                if (endFis != null)
                    endFis.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return _s.replace("\n", "") + _e.replace("\n", "");
    }

    public static String getCredential() {
        String credential = null;
        String credentialPath = Config.CONF_PASSWD_PATH + Config.SEPARATOR + Config.CONF_PASSWD_CREDENTIAL = null;
        String credentialIvPath = Config.CONF_PASSWD_PATH + Config.SEPARATOR + Config.CONF_PASSWD_CREDENTIAL_IV = null;
        File crf = new File(credentialPath);
        File ivf = new File(credentialIvPath);
        byte[] crfBuf = new byte[(int) crf.length()];
        byte[] ivfBuf = new byte[(int) ivf.length()];
        FileInputStream crFis = null;
        FileInputStream ivFis = null;
        String key = null, iv = null;
        try {
            if (crf.exists()) {
                crFis = new FileInputStream(crf);
                crFis.read(crfBuf);
                key = new String(crfBuf, "ISO-8859-1");
            } else {
                logger.error("credential文件不存在!");
                throw new RuntimeException("credential文件不存在!");
            }
            if (ivf.exists()) {
                ivFis = new FileInputStream(ivf);
                ivFis.read(ivfBuf);
                iv = Base64.encodeBase64String((Base64.encodeBase64String(new String(ivfBuf, "ISO-8859-1").getBytes(), false).getBytes()), false);
            } else {
                logger.error("credential.iv文件不存在!");
                throw new RuntimeException("credential.iv文件不存在!");
            }
            credential = decrypt(key, getCredentialPwd(), iv);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (crFis != null)
                    crFis.close();
                if (ivFis != null)
                    ivFis.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return credential;
    }

    public static byte[] getIvspec(byte[] keyByte) {
        byte[] desKeyBytes = new byte[16];
        if (keyByte.length >= 16) {
            System.arraycopy(keyByte, 0, desKeyBytes, 0, 16);
        } else {
            System.arraycopy(keyByte, 0, desKeyBytes, 0, keyByte.length);
            for (int i = keyByte.length; i < desKeyBytes.length; i++) {
                desKeyBytes[i] = 32;
            }
        }
        return desKeyBytes;
    }

    public static String encrypt(String plainText, String key) {
        return encrypt(plainText, key, key);
    }

    public static String encrypt(String plainText, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, makeKey(key), makeIv(iv));
            String encryp = "{AES}" + Base64.encodeBase64String(cipher.doFinal(plainText.getBytes()), false);
            return encryp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encodeText, String key) {
        return decrypt(encodeText, key, key);
    }

    public static String decrypt(String encodeText, String key, String iv) {
        try {
            encodeText = encodeText.replace("{AES}", "");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, makeKey(key), makeIv(iv));
            String decrypted = new String(cipher.doFinal(Base64.decodeBase64(encodeText)));
            return decrypted;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static AlgorithmParameterSpec makeIv(String key) {
        return new IvParameterSpec(getIvspec(key.getBytes()));
    }

    public static byte[] getDesKeyBytes(byte[] keyByte) {
        byte[] desKeyBytes = new byte[32];
        if (keyByte.length >= 32) {
            System.arraycopy(keyByte, 0, desKeyBytes, 0, 32);
        } else {
            System.arraycopy(keyByte, 0, desKeyBytes, 0, keyByte.length);
            for (int i = keyByte.length; i < desKeyBytes.length; i++) {
                desKeyBytes[i] = 32;
            }
        }
        return desKeyBytes;
    }

    static Key makeKey(String key) {
        byte[] desKeyBytes = getDesKeyBytes(key.getBytes());
        SecretKeySpec keyspec = new SecretKeySpec(desKeyBytes, "AES");
        return keyspec;
    }
*/}

