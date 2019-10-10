package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Answer;
import com.pnc.StackOverflow.Entities.Question;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface QuestionServiceInterface {
    public List<Question> getQuestions();
    public void createQuestions(Question question);
    public Optional<Question> getQuestion(String Id);
}
