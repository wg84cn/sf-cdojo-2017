/**
 * Project Name:iatp-basic-common
 * File Name:JSONFormat.java
 * Package Name:com.smart.platform.format
 * Date:2016年10月27日下午9:55:33
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.format;

import com.alibaba.fastjson.JSON;

/**
 * ClassName:JSONFormat <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年10月27日 下午9:55:33 <br/>
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public class JSONFormat
{
    public static String EMPTY_JSON_OBJ = "{}";
    
    public static <T> T parseJsonToObject(String jsonStr,  Class<T> clazz)
    {
        return JSON.parseObject(jsonStr, clazz);
    }
    
    /**
     * 得到格式化json数据 退格用\t 换行用\r
     */
    public static String format(String jsonStr)
    {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++)
        {
            char c = jsonStr.charAt(i);
            char lastChar = 0;
            if (level > 0)
            {
                lastChar = jsonForMatStr.charAt(jsonForMatStr.length() - 1);
                if('\n' == lastChar)
                {
                    jsonForMatStr.append(getLevelStr(level));
                }
            }
            switch (c)
            {
                case '{':
                    level++;
                    jsonForMatStr.append(c).append('\n').append(getLevelStr(level));
                    break;
                case '[':
                    jsonForMatStr.append(c);
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c);
                    if(lastChar != '"' || lastChar == ']')
                    {
                        jsonForMatStr.append("\n");
                    }
                    break;
                case '}':
                    jsonForMatStr.append("\n").append(c);
                    break;
                case ']':
                    level--;
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        
        return jsonForMatStr.toString();
        
    }
    
    private static String getLevelStr(int level)
    {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++)
        {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
