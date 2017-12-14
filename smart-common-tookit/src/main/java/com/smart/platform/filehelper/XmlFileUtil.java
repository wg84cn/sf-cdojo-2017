/**
 * Project Name:basicplatform
 * File Name:XmlFileUtil.java
 * Package Name:com.smart.platform.filehelper
 * Date:2016年8月16日下午6:21:42
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
*/

package com.smart.platform.filehelper;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author 01135912
 * @version
 * @since JDK 1.6
 */
public class XmlFileUtil implements XmlDocument
{
    @Override
    public void createXml(String fileName)
    {
        
    }
    
    @Override
    public void parserXml(String fileName)
    {
        
    }
    
    public static void main(String[] args)
        throws Exception
    {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("E:\\devbak\\朱玲瑶\\2016-08-30\\linux\\os\\10.116.19.224_linux-sec-check_2016-08-30.xml"));
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        for (Element child : childElements)
        {
            System.out.println(child.getName() + ": " + child.attributes());
            List<Element> eleList = child.elements();
            for(Element childc: eleList)
            {
                System.out.println(childc.getName() + ": " + childc.getText()); 

            }
        }
    }
}
