/**
 * Project Name:basicplatform
 * File Name:CvsFileReader.java
 * Package Name:com.smart.platform.filehelper
 * Date:2016年8月30日下午8:52:36
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.filehelper;
/**
 * ClassName:CvsFileReader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月30日 下午8:52:36 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For temporary using 
 * @author 01135912
 * @version 
 * @since JDK 1.6
 */
public class CvsFileUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(CvsFileUtil.class);
    
    private static Map<String, Set<String>> hashMap = new HashMap<String, Set<String>>();
    
    /**
     * 导出
     * 
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList)
    {
        boolean isSucess = false;
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try
        {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty())
            {
                for (String data : dataList)
                {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        }
        catch (Exception e)
        {
            isSucess = false;
        }
        finally
        {
            if (bw != null)
            {
                try
                {
                    bw.close();
                    bw = null;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (osw != null)
            {
                try
                {
                    osw.close();
                    osw = null;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                    out = null;
                }
                catch (IOException e)
                {
                    LOG.error(e.toString());
                }
            }
        }
        return isSucess;
    }
    
    /**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public static void importCsv(File file)
    {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] headerArray = line.split("\",\"");
                if (headerArray.length < 6)
                {
                    continue;
                }
                String ipKey = headerArray[6];
                String userIdValue = headerArray[0];
                if (ipKey == null)
                {
                    continue;
                }
                ipKey = ipKey.replace(" ", "");
                ipKey = ipKey.replace("\"", "");
                if (userIdValue != null)
                {
                    userIdValue = userIdValue.replace(" ", "");
                    userIdValue = userIdValue.replace("\"", "");
                    ;
                }
                if (ipKey.isEmpty())
                {
                    continue;
                }
                
                Set<String> userSet = hashMap.get(ipKey.trim());
                if (userSet == null)
                {
                    userSet = new HashSet<String>();
                }
                userSet.add(userIdValue);
                hashMap.put(ipKey.trim(), userSet);
            }
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                    br = null;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static Map<String, Set<String>> getSortMap()
    {
        ValueComparator bvc = new ValueComparator(hashMap);
        Map<String, Set<String>> sortedMap = new TreeMap<String, Set<String>>(bvc);
        sortedMap.putAll(hashMap);
        return sortedMap;
    }
}

class ValueComparator implements Comparator<String>
{
    Map<String, Set<String>> base;
    public ValueComparator(Map<String, Set<String>> base)
    {
        this.base = base;
    }
    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b)
    {
        if (base.get(a).size() >= base.get(b).size())
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}