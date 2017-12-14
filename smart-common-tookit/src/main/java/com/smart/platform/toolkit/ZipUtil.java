/**
 * Project Name:basicplatform
 * File Name:ZipUtil.java
 * Package Name:com.smart.platform.toolkit
 * Date:2016年9月8日下午2:17:32
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(ZipUtil.class);
    
    /**
     * 压缩后的文件名
     * 
     * @author 01135912
     * @date 2016-7-29 下午6:21:32
     * @param name
     * @return
     */
    public static String zipName(String name)
    {
        String prefix = "";
        if (name.indexOf(".") != -1)
        {
            prefix = name.substring(0, name.lastIndexOf("."));
        }
        else
        {
            prefix = name;
        }
        return prefix + ".zip";
    }
    
    public static void zipFolder(String inputFilePath, String zipFileName)
    {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists())
        {
            throw new RuntimeException("The oraginal file can not found!");
        }
        
        File basetarZipFile = new File(zipFileName).getParentFile();
        if (!basetarZipFile.exists() && !basetarZipFile.mkdirs())
        {
            throw new RuntimeException("The destination file can not create!");
        }
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        ZipOutputStream zOut = null;
        try
        {
            out = new FileOutputStream(new String(zipFileName.getBytes(StringUtil.ENCODE)));
            bos = new BufferedOutputStream(out);
            zOut = new ZipOutputStream(bos);
            zip(zOut, inputFile, inputFile.getName());
        }
        catch (Exception e)
        {
            LOG.error("zip folder error {}",e.toString());
        }
        finally
        {
            IOUtils.closeQuietly(zOut);
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(out);
        }
    }
    
    public static void zipFileList(List<String> filePaths, String zipFileName)
    {
        File basetarZipFile = new File(zipFileName).getParentFile();
        if (!basetarZipFile.exists() && !basetarZipFile.mkdirs())
        {
            throw new RuntimeException("The destination file can not create!");
        }
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        ZipOutputStream zOut = null;
        try
        {
            out = new FileOutputStream(new String(zipFileName.getBytes(StringUtil.ENCODE)));
            bos = new BufferedOutputStream(out);
            zOut = new ZipOutputStream(bos);
            simpleFileListZip(zOut, filePaths, zipFileName);
        }
        catch (Exception e)
        {
            LOG.error("zip file list error,{}", e.toString());
        }
        finally
        {
        	IOUtils.closeQuietly(zOut);
        	IOUtils.closeQuietly(bos);
        	IOUtils.closeQuietly(out);
        }
    }
    
    private static void zip(ZipOutputStream zOut, File file, String base)
    {
        try
        {
            if (file.isDirectory())
            {
                File[] listFiles = file.listFiles();
                zOut.putNextEntry(new ZipEntry(base + File.separator));
                base = (base.length() == 0) ? "" : base + File.separator;
                if (listFiles != null && listFiles.length > 0)
                {
                    for (File f : listFiles)
                    {
                        zip(zOut, f, base + f.getName());
                    }
                }
            }
            else
            {
                if (base == null || base.isEmpty())
                {
                    base = file.getName();
                }
                zOut.putNextEntry(new ZipEntry(base));
                writeFile(zOut, file);
            }
        }
        catch (IOException e)
        {
            LOG.error("zip file error {}", e.toString());
        }
    }
    
    private static void simpleFileListZip(ZipOutputStream zOut, List<String> filePaths, String base)
    {
        if (base == null || base.isEmpty())
        {
            throw new RuntimeException("The destination file path is invalid!");
        }
        
        for (String filePath : filePaths)
        {
            File file = new File(filePath);
            if (file.isDirectory() || !file.exists())
            {
                LOG.warn("zip can not find the file :{}", file.getPath());
                continue;
            }
            try
            {
                zOut.putNextEntry(new ZipEntry(file.getName()));
            }
            catch (IOException e)
            {
                LOG.error("zip file error {}", e.toString());
            }
            writeFile(zOut, file);
        }
        LOG.info("zip done!");
    }
    
    private static void writeFile(ZipOutputStream zOut, File file)
    {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try
        {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int len = 0;
            byte[] buff = new byte[2048];
            while ((len = bis.read(buff)) != -1)
            {
                zOut.write(buff, 0, len);
            }
            zOut.flush();
        }
        catch (IOException ex)
        {
            LOG.error("write file erorr {}", ex.toString());
        }
        finally
        {
        	IOUtils.closeQuietly(bis);
        	IOUtils.closeQuietly(fis);
        }
    }
    
    /****
     * 解压
     *
     * @param zipPath zip文件路径
     * @param destinationPath 解压的目的地点
     * @param ecode 文件名的编码字符集
     */
    public static void unZip(String zipPath, String destinationPath)
    {
        File zipFile = new File(zipPath);
        if (!zipFile.exists())
        {
            throw new RuntimeException("zip file " + zipPath + " does not exist.");
        }
        Project proj = new Project();
        Expand expand = new Expand();
        expand.setProject(proj);
        expand.setTaskType("unzip");
        expand.setTaskName("unzip");
        expand.setSrc(zipFile);
        expand.setDest(new File(destinationPath));
        expand.setEncoding(StringUtil.ENCODE);
        expand.execute();
        LOG.info("unzip done!");
    }
}