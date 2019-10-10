package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService implements AnswerServiceInterface {
    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public Optional<Question> getAnswers(String id) {
        return questionRepository.findById(id);
    }
}
