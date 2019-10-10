package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Service.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("StackOverflow/Questions")
public class QuestionController {
    @Autowired
    QuestionServiceImpl questionServiceImpl;

    @GetMapping
    public List<Question> getAll() {
        return questionServiceImpl.getQuestions();
    }

    @GetMapping("/{Id}")
    public Optional<Question> getQuestionById(@PathVariable String Id) {

        Optional<Question> question = questionServiceImpl.getQuestion(Id);
        question.get().setAnswerCount(question.get().getAnswers().size());
        return question;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveQuestion(@RequestBody Question question) {

        questionServiceImpl.createQuestions(question);
    }
}
