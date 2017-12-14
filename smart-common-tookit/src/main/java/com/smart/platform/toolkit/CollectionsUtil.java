/**
 * Project Name:basicplatform
 * File Name:CollectionsUtil.java
 * Package Name:com.smart.platform.toolkit
 * Date:2016年8月17日下午8:24:32
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import java.util.Set;

/**
 * ClassName:CollectionsUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年8月17日 下午8:24:32 <br/>
 * 
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public final class CollectionsUtil
{
    public static void addDataToList(List arrayList, Object element)
    {
        if (arrayList == null)
        {
            arrayList = new ArrayList();
        }
        if (element != null)
        {
            arrayList.add(element);
        }
    }
    
    public static String listToString(Collection<String> inputList, String defaultValue)
    {
        if (inputList == null || inputList.isEmpty())
        {
            return defaultValue;
        }
        
        StringBuilder sbder = new StringBuilder();
        for (String inputStr : inputList)
        {
            if (inputStr == null || inputStr.trim().isEmpty())
            {
                continue;
            }
            sbder.append(inputStr).append(",");
        }
        String listStr = sbder.toString();
        
        if (listStr.length() == 0)
        {
            return defaultValue;
        }
        return listStr.substring(0, listStr.length() - 1);
    }
    
    public static List<String> inputStrToList(Collection<String> inputList, String defaultValue)
    {
        if (inputList == null || inputList.isEmpty())
        {
            inputList = new ArrayList<String>();
            inputList.add(defaultValue);
        }
        return (List<String>)inputList;
    }
    
    public static boolean isBlank(List<?> inputList)
    {
        return inputList == null || inputList.isEmpty();
    }
    
    public static boolean noMoreThanSize(List<?> inputList, int size)
    {
        if (inputList == null || inputList.isEmpty())
        {
            return true;
        }
        return inputList.size() <= size;
    }
    
    public static Map<String, Set<String>> valueListToMap(List<Map<String, Set<String>>> mapValueList)
    {
        Map<String, Set<String>> mapValues = new HashMap<String, Set<String>>();
        for (Map<String, Set<String>> eachMapValues : mapValueList)
        {
            if (eachMapValues == null || eachMapValues.isEmpty())
            {
                continue;
            }
            Set<Entry<String, Set<String>>> entrySet = eachMapValues.entrySet();
            for (Entry<String, Set<String>> entry : entrySet)
            {
                Set<String> existValue = mapValues.get(entry.getKey());
                if (existValue == null)
                {
                    existValue = new HashSet<String>();
                    mapValues.put(entry.getKey(), existValue);
                }
                existValue.addAll(entry.getValue());
            }
        }
        return mapValues;
    }
    
    public static Map<String, Set<String>> reputValueToMap(Map<String, Set<String>> mapValues, String exitKey,
        Map<String, Set<String>> newValues, String newKey)
    {
        if (newValues == null || newValues.isEmpty())
        {
            return mapValues;
        }
        Set<String> valueSet = newValues.get(newKey);
        if (valueSet != null && !valueSet.isEmpty())
        {
            mapValues.put(exitKey, valueSet);
        }
        return mapValues;
    }
    
    /**
     * putSetToMap:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author 01135912
     * @param policyMap
     * @param policyName
     * @param string
     * @since JDK 1.6
     */
    public static void putSetToMap(Map<String, Set<String>> existMap, String key, String value)
    {
        if (StringUtil.isEmpty(value) || StringUtil.isEmpty(key))
        {
            return;
        }
        Set<String> existSet = existMap.get(key);
        if (existSet == null)
        {
            existSet = new HashSet<String>();
        }
        existSet.add(value);
        existMap.put(key, existSet);
    }
    
    /**
     * filterList:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author 01135912
     * @param collectList
     * @param mustFiled
     * @return
     * @since JDK 1.6
     */
    public static List<String> filterList(List<String> collectList, int mustFiled)
    {
        if (collectList.size() <= mustFiled || mustFiled == 0)
        {
            return collectList;
        }
        
        for (int idx = 0; idx < collectList.size(); idx++)
        {
            if ((idx + 1) % mustFiled == 0)
            {
                collectList.remove(idx);
            }
        }
        return collectList;
    }
}
