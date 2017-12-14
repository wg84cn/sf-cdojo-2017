/**
 * Project Name:basicplatform
 * File Name:XmlDocument.java
 * Package Name:com.smart.platform.filehelper
 * Date:2016年8月16日下午6:29:08
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.filehelper;

/**
 * ClassName:XmlDocument <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年8月16日 下午6:29:08 <br/>
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public interface XmlDocument
{
    /**
     * 建立XML文档
     * 
     * @param fileName 文件全路径名称
     */
    public void createXml(String fileName);
    
    /**
     * 解析XML文档
     * 
     * @param fileName 文件全路径名称
     */
    public void parserXml(String fileName);
}
