package com.sf.iguess.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sf.iguess.survey.domain.Answer;

@Mapper
public interface AnswerDao {
    public boolean save(Answer answer);
    
    public Answer findById(String id);

    public List<Answer> findByScoreId(String scoreId);
    
    public boolean update(Answer answer);
    
}