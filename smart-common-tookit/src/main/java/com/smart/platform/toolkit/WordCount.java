/**
 * Project Name:common-tookit
 * File Name:WordCount.java
 * Package Name:com.smart.platform.toolkit
 * Date:2017年12月5日下午6:50:02
 * Copyright (c) 2017, 01135912 All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName:WordCount <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason:	 ADD REASON. <br/>
 * Date:     2017年12月5日 下午6:50:02 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class WordCount
{
 private static Map<String, Integer> wordCountMap = new HashMap<>(60000);
    
    private static final String chininseReg = "[^\u4e00-\u9fa5]";
    
    private static final String filePath = "E:/share/wordcount/题目.txt";
    
    private static final Logger LOG = Logger.getLogger("IsicWordCount");
    
    private WordCount()
    {
    }
    
    public static void main(String[] args)
    {
        initWordCountMap(filePath);
    }
    
    private static void initWordCountMap(String filePath)
    {
        BufferedReader reader = null;
        InputStreamReader isr = null;
        try
        {
            isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
            reader = new BufferedReader(isr);
            String eachLine = null;
            while ((eachLine = reader.readLine()) != null)
            {
                eachLine = eachLine.replaceAll(chininseReg, "");
                workLineSplit(eachLine);
            }
            Integer[] topTenCountArray = getTopTenCountArray();
            outputTopTenWords(topTenCountArray);
        }
        catch (Exception e)
        {
            LOG.log(Level.WARNING, "init word map error", e);
        }
        finally
        {
            
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
                if (isr != null)
                {
                    isr.close();
                }
            }
            catch (IOException e)
            {
                LOG.log(Level.WARNING, "init word map with close buffer error", e);
            }
        }
        
    }
    
    /**
     * workLineSplit:(文字分割并组装进map). <br/>
     * 
     * @author 01135912
     * @param line
     * @since JDK 1.7
     */
    private static void workLineSplit(String line)
    {
        if (line == null || line.trim().isEmpty())
        {
            return;
        }
        char[] lineCharArray = line.toCharArray();
        for (char lineChar : lineCharArray)
        {
            String lineCharStr = Character.toString(lineChar);
            Integer count = wordCountMap.get(lineCharStr);
            if (count == null)
            {
                wordCountMap.put(lineCharStr, 1);
            }
            else
            {
                wordCountMap.put(lineCharStr, (count + 1));
            }
        }
    }
    
    /**
     * getTopTenCountArray:(这里用一句话描述这个方法的作用). <br/>
     * 获取前10 的数字数组
     * 
     * @author 01135912
     * @return
     * @since JDK 1.7
     */
    private static Integer[] getTopTenCountArray()
    {
        // 分词数字结果排序
        Collection<Integer> countValues = wordCountMap.values();
        List<Integer> countValuesList = new ArrayList<>();
        for (Integer countInt : countValues)
        {
            countValuesList.add(countInt);
        }
        Collections.sort(countValuesList);
        
        // 取出前10的数字
        Integer[] topTenArray = new Integer[10];
        int countValueSize = countValuesList.size() - 1;
        for (int index = countValueSize; index > countValueSize - 10; index--)
        {
            int countIndex = countValueSize - index;
            topTenArray[countIndex] = countValuesList.get(index);
        }
        return topTenArray;
    }
    
    /**
     * outputTopTenWords:(这里用一句话描述这个方法的作用). <br/>
     * 输出最最总的top ten 文字
     * 
     * @author 01135912
     * @param topTenArray
     * @since JDK 1.7
     */
    private static void outputTopTenWords(Integer[] topTenArray)
    {
        int topTenMinNumber = topTenArray[9];
        Map<Integer, String> countTopTenWordMap = new HashMap<>();
        // 取出前10的key value;
        Set<Entry<String, Integer>> wordEntry = wordCountMap.entrySet();
        for (Entry<String, Integer> entry : wordEntry)
        {
            Integer wordCount = entry.getValue();
            if (wordCount < topTenMinNumber)
            {
                continue;
            }
            countTopTenWordMap.put(entry.getValue(), entry.getKey());
        }
        
        for (int countIdx = 0; countIdx < 10; countIdx++)
        {
            Integer wordTopEachCount = topTenArray[countIdx];
            String wordStr = countTopTenWordMap.get(wordTopEachCount);
            String eachCountStr = "key " + wordStr + "\t value" + wordTopEachCount;
            LOG.log(Level.INFO, eachCountStr);
        }
    }
}

