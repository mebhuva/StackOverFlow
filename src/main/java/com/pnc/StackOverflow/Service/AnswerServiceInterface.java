package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Answer;
import com.pnc.StackOverflow.Entities.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerServiceInterface {
    public Optional<Question> getAnswers(String id);
}
