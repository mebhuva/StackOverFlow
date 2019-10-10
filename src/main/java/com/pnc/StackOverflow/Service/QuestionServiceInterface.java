package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Answer;
import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface QuestionServiceInterface {
    public List<Question> getQuestions(String token)throws StackOverflowException;
    public void createQuestions(Question question, String token)throws StackOverflowException;
    public void addLike(String Id,String token)throws StackOverflowException;
    public Optional<Question> getQuestion(String Id, String token)throws StackOverflowException;
}
