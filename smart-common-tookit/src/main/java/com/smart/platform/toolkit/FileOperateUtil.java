/**
 * Project Name:basicplatform
 * File Name:FileOperateUtil.java
 * Package Name:com.smart.platform.toolkit
 * Date:2016年9月8日下午1:44:46
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

/**
 * ClassName:FileOperateUtil <br/>
 * Date:     2016年9月8日 下午1:44:46 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author 01135912
 * @version
 * @since JDK 1.6
 */
public class FileOperateUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(FileOperateUtil.class);
    
    private static final String REALNAME = "realName";
    
    private static final String STORENAME = "storeName";
    
    private static final String SIZE = "size";
    
    private static final String SUFFIX = "suffix";
    
    private static final String CONTENTTYPE = "contentType";
    
    private static final String CREATETIME = "createTime";
    
    private static final String UPLOADDIR = "uploadDir/";
    
    private static final int MAX_RANDOM_NUM = 1000000;
    
    /**
     * 将上传的文件进行重命名
     * 
     * @author 01135912
     * @date 2016-7-29 下午6:21:32
     * @param name
     * @return
     */
    private static String rename(String name)
    {
        String currTimeStr = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS);
        int random = (int)(Math.random() * MAX_RANDOM_NUM);
        String fileName = currTimeStr + random;
        if (name.indexOf(".") != -1)
        {
            fileName += name.substring(name.lastIndexOf("."));
        }
        return fileName;
    }
    
    /**
     * 上传文件
     * 
     * @author 01135912
     * @date 2016-7-29 下午6:21:32
     * @param request
     * @param params
     * @param values
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> upload(HttpServletRequest request, String[] params,
        Map<String, Object[]> values)
        throws Exception
    {
        
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        
        String uploadDir = request.getSession().getServletContext().getRealPath("/") + FileOperateUtil.UPLOADDIR;
        File file = new File(uploadDir);
        
        if (!file.exists())
        {
            file.mkdir();
        }
        
        String fileName = null;
        int i = 0;
        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++)
        {
            
            Map.Entry<String, MultipartFile> entry = it.next();
            MultipartFile mFile = entry.getValue();
            
            fileName = mFile.getOriginalFilename();
            
            String storeName = rename(fileName);
            
            String noZipName = uploadDir + storeName;
            String zipName = ZipUtil.zipName(noZipName);
            
            // 上传成为压缩文件
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
            outputStream.putNextEntry(new ZipEntry(fileName));
            outputStream.setEncoding(StringUtil.ENCODE);
            
            FileCopyUtils.copy(mFile.getInputStream(), outputStream);
            
            Map<String, Object> map = new HashMap<String, Object>();
            // 固定参数值对
            map.put(FileOperateUtil.REALNAME, ZipUtil.zipName(fileName));
            map.put(FileOperateUtil.STORENAME, ZipUtil.zipName(storeName));
            map.put(FileOperateUtil.SIZE, new File(zipName).length());
            map.put(FileOperateUtil.SUFFIX, "zip");
            map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
            map.put(FileOperateUtil.CREATETIME, new Date());
            
            // 自定义参数值对
            for (String param : params)
            {
                map.put(param, values.get(param)[i]);
            }
            
            result.add(map);
        }
        return result;
    }
    
    /**
     * download:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author 01135912
     * @param request
     * @param response
     * @param downFile
     * @param contentType
     * @since JDK 1.6
     */
    public static void download(HttpServletResponse response, File downFile,
        String contentType)
    {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding(StringUtil.ENCODE);
            response.setContentType(contentType);
            response.setHeader("Content-disposition",
                "attachment; filename=" + new String(downFile.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(downFile.length()));
            bis = new BufferedInputStream(new FileInputStream(downFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
            {
                bos.write(buff, 0, bytesRead);
            }
        }
        catch (IOException ex)
        {
            LOG.error(" Download zip file error {}", ex.toString());
        }
        finally
        {
        	IOUtils.closeQuietly(bis);
        	IOUtils.closeQuietly(bos);
        }
    }
    
    /**
     * createFilenName:(这里用一句话描述这个方法的作用). <br/>
     * @author 01135912
     * @param file
     * @param req
     * @param appendFolder
     * @param uploadType
     * @return
     * @since JDK 1.6
     */
    public static String createFilenName(MultipartFile file, String filePath,String fileExtra)
    {
        String fileName = "";
        if(file != null)
        {
            fileName  = file.getOriginalFilename();
        }
        String subFix = ".";
        if(fileName != null && !fileName.trim().isEmpty())
        {
            subFix = fileName.substring(fileName.lastIndexOf('.'));
        }
        
        if(fileExtra == null)
        {
            fileExtra = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return StringUtil.builderStrWithCurrentTime(filePath, fileExtra) + subFix;
    }
    
    /**
     * getLastDayPath:(这里用一句话描述这个方法的作用). <br/>
     * @author 01135912
     * @param fileRoot
     * @param fileFolder
     * @return
     * @since JDK 1.6
     */
    public static String getLastDayPath(String fileRoot, String fileFolder)
    {
        if (fileFolder == null)
        {
            throw new IllegalArgumentException("Can not find the type name with type value:" + fileFolder);
        }
        File file = new File(fileRoot + fileFolder);
        
        if (file == null || file.isFile() || file.list() == null || file.list().length == 0)
        {
            //throw new IllegalArgumentException("No file need to check with :" + file.getPath());
            LOG.warn("No file need to check with :" , file.getPath());
            return null;
        }
        File[] fileList = file.listFiles();
        String fileMaxName = fileList[0].getName();
        for (File dateFile : fileList)
        {
            if (dateFile.isFile())
            {
                continue;
            }
            if (fileMaxName.compareTo(dateFile.getName()) < 0)
            {
                fileMaxName = dateFile.getName();
            }
        }
        return file.getPath() + File.separatorChar + fileMaxName;
    }
    
    public static String appendSubFolder(String inputRoot, String rootWithFolder)
    {
        int pathLength = rootWithFolder.length();
        int existIdx = rootWithFolder.lastIndexOf(File.separatorChar);
        if(existIdx == -1)
        {
            throw new IllegalArgumentException("File path not contains the 'file separator char'");
        }
        String folderName = rootWithFolder.substring(existIdx, pathLength);
        return inputRoot + folderName;
    }
    
    public static Collection<String> getLastMonthFiles(String pathEndDate)
    {
        int splitDateIdx = pathEndDate.lastIndexOf(File.separator);
        String rootPath = pathEndDate.substring(0,splitDateIdx);
        String dateStr = pathEndDate.substring(splitDateIdx+1);
        String lastDateStr = DateUtil.addDay(dateStr, DateUtil.MONTH_MAX_DAYS * -1, DateStyle.YYYY_MM_DD);
        List<String> monthFolders = getMonthsRootFolder(new File(rootPath).listFiles(), dateStr, lastDateStr);
        Collections.sort(monthFolders);
        return extractMonthsFilePath(rootPath, monthFolders);
    }
    
    private static List<String> getMonthsRootFolder(File[] rootFolders, String dateStr, String lastDateStr)
    {
        if (rootFolders == null || rootFolders.length == 0)
        {
            return null;
        }
        List<String> fileRootList = new ArrayList<String>();
        for (File file : rootFolders)
        {
            if (!file.isDirectory())
            {
                continue;
            }
            String fileName = file.getName();
            if (fileName.compareTo(dateStr) <= 0 && fileName.compareTo(lastDateStr) >= 0)
            {
                fileRootList.add(fileName);
            }
        }
        return fileRootList;
    }
    
    private static Collection<String> extractMonthsFilePath(String rootPath, List<String> fileFolders)
    {
        Map<String,String> fileMap = new HashMap<String,String>();
        for(String folderName:fileFolders)
        {
            File[] destFiles = new File(rootPath+File.separator+folderName).listFiles();
            for(File destFile:destFiles)
            {
                if (destFile.isDirectory() || !destFile.getName().endsWith(".log"))
                {
                    continue;
                }
                fileMap.put(destFile.getName(), destFile.getAbsolutePath());
            }
        }
        return fileMap.values();
    }
}
