package com.smart.platform.filehelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TextFileUtil
{
    
    private static final Logger LOG = LoggerFactory.getLogger(TextFileUtil.class);
    
    public static List<String> readFile(String filePath)
    {
        List<String> fileLines = null;
        try
        {
            fileLines = FileUtils.readLines(new File(filePath), "UTF-8");
        }
        catch (Throwable e)
        {
            LOG.error("file write exception:" + e.toString());
        }
        return fileLines;
    }
    
    public static String readFileAsString(String filePath)
    {
        String fileLineStr = null;
        try
        {
            fileLineStr = FileUtils.readFileToString(new File(filePath), "UTF-8");
        }
        catch (Throwable e)
        {
            LOG.error("file read as string exception:" + e.toString());
        }
        return fileLineStr;
    }
    
    public static void writeFile(String filePath, List<String> fileLines)
    {
        try
        {
            FileUtils.writeLines(new File(filePath), "UTF-8", fileLines);
        }
        catch (IOException e)
        {
            LOG.error("file write exception:" + e.toString());
        }
    }
    
    public static byte[] createTextByteArray(List<List<String>> collectStrsList, String collectTextSplit)
    {
        if (collectStrsList == null || collectStrsList.isEmpty())
        {
            return null;
        }
        StringBuilder sbder = new StringBuilder();
        for (List<String> strList : collectStrsList)
        {
            for (int idx = 0; idx < strList.size(); idx++)
            {
                sbder.append(strList.get(idx));
                if (idx == strList.size() - 1)
                {
                    continue;
                }
                sbder.append(collectTextSplit);
            }
            sbder.append("\r");
        }
        return sbder.toString().getBytes();
    }
}
