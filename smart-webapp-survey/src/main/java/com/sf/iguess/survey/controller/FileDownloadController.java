package com.sf.iguess.survey.controller;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.iguess.survey.domain.Answer;
import com.sf.iguess.survey.service.AnswerService;
import com.smart.platform.toolkit.StringUtil;

/**
 * @date 2017年12月4日
 * @time 下午7:34:38
 * @description
 */

@Controller
public class FileDownloadController
{
    @Resource
    private AnswerService answerService;
    
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
    
    @RequestMapping("/download/attaches/{questionId}")
    public ResponseEntity<byte[]> questionAttaches(@PathVariable("questionId") String questionId)
    {
        HttpHeaders headers = new HttpHeaders();
        Answer answer = answerService.findById(questionId);
        if (answer == null || StringUtil.isEmpty(answer.getFileLink()))
        {
            return null;
        }
        String fileLink = answer.getFileLink();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String charset = null;
        try
        {
            charset = new String(fileLink.getBytes("utf-8"), "iso-8859-1");
            headers.setContentDispositionFormData("file", charset);
            File file = new File(fileLink);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            logger.error("file download error {}", e);
            return null;
        }
    }
    
}
