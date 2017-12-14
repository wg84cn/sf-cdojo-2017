/**
 * Project Name:basicplatform
 * File Name:StringEncodeTest.java
 * Package Name:com.smart.platform.formative
 * Date:2016年9月10日下午12:53:23
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.formative;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.smart.platform.toolkit.RegularMatcher;
import com.smart.platform.toolkit.XssShieldUtil;
import com.smart.platform.toolkit.XssWhiteListUtil;

import junit.framework.Assert;

/**
 * ClassName:StringEncodeTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年9月10日 下午12:53:23 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class StringEncodeTest
{
    @Test
    public void testStr() {
        String testStr = "中";
        try {
            // 得到指定编码的字节数组 字符串--->字节数组
            byte[] t_iso = testStr.getBytes("ISO8859-1");
            byte[] t_gbk = testStr.getBytes("GBK");
            byte[] t_utf8 = testStr.getBytes("UTF-8");
            System.out.println("使用ISO解码..." + t_iso.length);
            System.out.println("使用GBK解码..." + t_gbk.length);
            System.out.println("使用UTF8解码..." + t_utf8.length);
            // 解码后在组装
            String ut_iso = new String(t_iso, "ISO8859-1");
            String ut_gbk = new String(t_gbk, "GBK");
            String ut_utf8 = new String(t_utf8, "UTF-8");
            System.out.println("使用ISO解码后再用ISO组装..." + ut_iso);
            System.out.println("使用GBK解码后再用GBK组装..." + ut_gbk);
            System.out.println("使用UTF8解码后再用UTF8组装..." + ut_utf8);
            // 有时候要求必须是iso字符编码类型
            // 可以先用GBK/UTF8编码后，用ISO8859-1组装成字符串，解码时逆向即可获得正确中文字符
            String t_utf8Toiso = new String(t_utf8, "ISO8859-1");
            // 将iso编码的字符串进行还原
            String ut_utf8Toiso = new String(t_utf8Toiso.getBytes("ISO8859-1"),"UTF-8");
            System.out.println("使用ISO组装utf8编码字符..." + t_utf8Toiso);
            System.out.println("使用ISO解码utf8编码字符..." + ut_utf8Toiso);
        } catch (UnsupportedEncodingException e) {
            Assert.fail();
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void testJsonStr() {
        String formateStr = (String)JSON.parse("\"成都市字符串\"");
        System.out.println(formateStr);
    }
    
    @Test
    public void testMathStr()
    {
        String EQUAL_FLAG_MATCH = "^(为空|空|\\s*|null)$";
        List<String> inputList = new ArrayList<String>();
        inputList.add("为空");
        inputList.add("空");
        inputList.add("");
        inputList.add(" ");
        inputList.add("  ");
        inputList.add("null");
        inputList.add("Null");
        inputList.add("NULL");
        inputList.add(null);

        for(String inputStr:inputList)
        {
            System.out.println(RegularMatcher.isRegularMatch(inputStr, EQUAL_FLAG_MATCH));
        }
        
        System.out.println("--------------------------");
        List<String> inputList1 = new ArrayList<String>();
        inputList1.add("为空1");
        inputList1.add("1空");
        inputList1.add(" 1");
        inputList1.add("1 ");
        inputList1.add(" 1 ");
        inputList1.add("null空");
        inputList1.add("空Null");
        inputList1.add("为空NULL");
        inputList1.add("s");
        inputList1.add("dfasdfasdf");

        for(String inputStr:inputList1)
        {
            System.out.println(RegularMatcher.isRegularMatch(inputStr, EQUAL_FLAG_MATCH));
        }
    }
    
    @Test
    public void testEqaul()
    {
        String GREAT_EQUAL_THAN_MATCH = "^(>=|大于等于)$";
        String LESS_EQUAL_THAN_MATCH = "^(<=|小于等于)$";
        String GREAT_THAN_MATCH = "^(>|大于)$";
        String LESS_THAN_MATCH = "^(<|小于)$";
        String INCLUDE_FLAG_MATCH = "^(in|include|包含)$";
        
        List<String> inputList = new ArrayList<String>();
        inputList.add("in");
        inputList.add("include");
        inputList.add("包含");

        for(String inputStr:inputList)
        {
            System.out.println(RegularMatcher.isRegularMatch(inputStr, INCLUDE_FLAG_MATCH));
        }
        
        System.out.println("--------------------------");
        List<String> inputList1 = new ArrayList<String>();
        inputList1.add("< =");
        inputList1.add("小于 等于");
        inputList1.add(" 1");

        for(String inputStr:inputList1)
        {
            System.out.println(RegularMatcher.isRegularMatch(inputStr, INCLUDE_FLAG_MATCH));
        }
    }
    
    @Test
    public void testValidRequestParms()
    {
            String value = null;
            value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);</script>");
            System.out.println("type-1: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<script src='' onerror='alert(document.cookie)'></script>");
            System.out.println("type-2: '" + value + "'");
            
            value = XssShieldUtil.stripXss("</script>");
            System.out.println("type-3: '" + value + "'");
            
            value = XssShieldUtil.stripXss(" eval(abc);");
            System.out.println("type-4: '" + value + "'");
            
            value = XssShieldUtil.stripXss(" expression(abc);");
            System.out.println("type-5: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<img src='111111111' onerror='alert(document.cookie);'></img>");
            System.out.println("type-6: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'/>");
            System.out.println("type-7: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'>");
            System.out.println("type-8: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);");
            System.out.println("type-9: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<script>window.location='url'");
            System.out.println("type-10: '" + value + "'");
            
            value = XssShieldUtil.stripXss(" onload='alert(\"abc\");");
            System.out.println("type-11: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<img src=x<!--'<\"-->>");
            System.out.println("type-12: '" + value + "'");
            
            value = XssShieldUtil.stripXss("<=img onstop=");
            System.out.println("type-13: '" + value + "'");
    }
    
    @Test
    public void testValidWhiteParms(){
		String htmlStr = "<iframe>bb<img style='display:inline;' alt='[挤眼]' src='http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c3/zy_thumb.gif' height='22' width='22' />bb<img style='display:inline;' alt='[挤眼]' src='http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c3/zy_thumb.gif' height='22' width='22' />aaaa</iframe>";
		//System.out.println(XssWhiteListUtil.whiteListXss(htmlStr));
		String encodeStr = "\"><iframe/src=data:text/html;base64,PGJvZHkgb25sb2FkPWFsZXJ0KDEpPg>";
		System.out.println(XssWhiteListUtil.whiteListXss(encodeStr));
	}
}

