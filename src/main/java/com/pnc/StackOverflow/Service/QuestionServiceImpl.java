package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionServiceInterface {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestion(String Id) {
        return questionRepository.findById(Id);
    }



    @Override
    public void createQuestions(Question question) {
        questionRepository.save(question);
    }


}
