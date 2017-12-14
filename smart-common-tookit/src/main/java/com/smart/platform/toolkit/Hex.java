/**   
 * @Title: Hex.java 
 * @Package: com.smart.platform.formative 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年8月9日 下午10:37:57 
 * @version 1.3.1 
 */

package com.smart.platform.toolkit;

import java.math.BigInteger;

/**
 * @Description
 * @author Administrator
 * @date 2016年8月9日 下午10:37:57
 * @version V1.3.1
 */
public final class Hex
{
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c','d','e','f'};

    public static String encodeToString(Long inputInt) throws Exception
    {
        char[] encodedChars = encode(String.valueOf(inputInt).getBytes("UTF-8"));
        return new String(encodedChars);
    }
    
    public static Long decodeToLong(String intStr) throws Exception
    {
    	byte[] encodedChars = decode(intStr.getBytes("UTF-8"));
        return Long.parseLong(new String(encodedChars,"UTF-8"));
    }
    
    public static String encodeToString(byte[] bytes)
    {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }
    
    public static String byte2hex(byte[] b) 
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
            {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
    
    /**
     * 转化十六进制
     * 
     * @param array
     * @return
     */
    public static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else
        {
            return hex;
        }
    }
    
    public static char[] encode(byte[] data)
    {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++)
        {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
    
    public static byte[] decode(byte[] array)
        throws Exception
    {
        return decode(new String(array, "UTF-8"));
    }
    
    public static byte[] decode(String hex)
    {
        return decode(hex.toCharArray());
    }
    
    public static byte[] decode(char[] data)
        throws IllegalArgumentException
    {
        
        int len = data.length;
        
        if ((len & 0x01) != 0)
        {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        
        byte[] out = new byte[len >> 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++)
        {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte)(f & 0xFF);
        }
        return out;
    }
    
    protected static int toDigit(char ch, int index)
        throws IllegalArgumentException
    {
        int digit = Character.digit(ch, 16);
        if (digit == -1)
        {
            throw new IllegalArgumentException("Illegal hexadecimal charcter " + ch + " at index " + index);
        }
        return digit;
    }
}
