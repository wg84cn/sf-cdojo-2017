package com.sf.iguess.survey.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sf.iguess.survey.constant.Constant;
import com.sf.iguess.survey.domain.Answer;
import com.sf.iguess.survey.domain.UserScore;
import com.sf.iguess.survey.mapper.AnswerDao;
import com.sf.iguess.survey.service.AnswerService;
import com.sf.iguess.survey.service.UserScoreService;

@Service
public class StoreServiceImpl implements AnswerService
{
    
    @Resource
    private AnswerDao answerDao;
    
    @Resource
    private UserScoreService userScoreService;
    
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    
    public void saveAnswerList(UserScore userScore, List<Answer> answerList, MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                String[] fileName = file.getOriginalFilename().split("\\.");
                Integer index = Integer.parseInt(fileName[0])-1;
                File targetFile = fileHandle(file);
                if(targetFile != null) {
                    Answer answer = answerList.get(index);
                    answer.setFileLink(targetFile.getPath());
                    answer.setFileName(file.getOriginalFilename());
                }
            } catch (Exception e) {
                logger.error("save answer error {}", e);
            }
        }
        int count = countPoint(answerList);
        userScore.setScore(count);
        userScore.setStatus(count > 59?1:0);
        saveAnswer(answerList, userScore);
    }
    
    @Override
    public boolean update(Answer answer)
    {
        return answerDao.update(answer);
    }
    
    @Override
    public Answer findById(String answerId)
    {
        return answerDao.findById(answerId);
    }
    
    @Override
    public List<Answer> findByScoreId(String scoreId)
    {
        return answerDao.findByScoreId(scoreId);
    }
    
    @Override
    public void saveAnswer(List<Answer> answerList, UserScore userScore)
    {
        userScoreService.save(userScore);
        
        for (Answer answer : answerList)
        {
            answer.setScoreId(userScore.getScoreId());
            save(answer);
        }
    }
    
    private boolean save(Answer answer)
    {
        String answerId = answer.getId();
        if (null == answerId)
        {
            answer.setId(UUID.randomUUID().toString());
            return answerDao.save(answer);
        }
        else
        {
            return update(answer);
        }
    }
    
    
    /**
     * 处理文件信息，并创建相应文件夹
     * @param file
     * @param menuId        指定路径下，以menuId
     * @param path          linux/window的指定路径
     * @param folderName    以固定文件名命名，null则用年月命名
     * 
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    private File fileHandle(MultipartFile file) throws IllegalStateException, IOException {
        long curTime = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        String[] fileNameArr = fileName.split(Constant.SUFFIXRULE);
        String randomNum = randomNum();
        
        String path = Constant.getOsPath();
        
        String ruleFileName = fileNameArr[0] + curTime + randomNum;
        String md5Name = DigestUtils.md5DigestAsHex(ruleFileName.getBytes());
        fileName = md5Name + "." + fileNameArr[1];
        path = getPath(path);
        if(null == path){
            return null;
        }
        File targetFile = new File(path, fileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        return targetFile;
    }
    
    /**
     * 获取当日文件夹路径
     * @param home
     * @param path
     * @return
     */
    private String getPath(String path) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);
        
        path += year+"-"+month+"-"+date;
        
        return path;
    }

    /**
     * 生成4位数随机数
     * @return
     */
    private String randomNum(){
        Random random = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            randomNum += String.valueOf(random.nextInt(9));
        }
        return randomNum;
    }
    
    /**
     * 计算得分
     * @param answerList
     * @return
     */
    private int countPoint(List<Answer> answerList) {
        int count = 1;
        for (Answer answer : answerList) {
            count += answer.getChoseValue();
            Integer serialNumber = answer.getSerialNumber();
            if("1".equals(serialNumber.toString())){
                Integer point = answer.getChoseValue();
                String reply = answer.getReply();
                if(point == 3 || reply.contains("等级保护")){
                    count += 5;
                }
            }
        }
        return count;
    }
}
