package com.sf.iguess.survey.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sf.iguess.survey.domain.Answer;
import com.sf.iguess.survey.domain.UserScore;

public interface AnswerService {
	
    public void saveAnswerList(UserScore userScore, List<Answer> answerList, MultipartFile[] files);
    
	public boolean update(Answer answer);
	
	public Answer findById(String answerId);
	
	public List<Answer> findByScoreId(String scoreId);
	
	public void saveAnswer(List<Answer> answerList,UserScore userScore);
}
