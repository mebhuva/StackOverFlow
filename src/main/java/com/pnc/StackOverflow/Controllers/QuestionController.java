package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
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
    public List<Question> getAll( @RequestHeader String token) throws StackOverflowException {
        return questionServiceImpl.getQuestions(token);
    }

    @GetMapping("/{Id}")
    public Optional<Question> getQuestionById(@PathVariable String Id,  @RequestHeader String token) throws StackOverflowException {

        Optional<Question> question = questionServiceImpl.getQuestion(Id, token);
        question.get().setAnswerCount(question.get().getAnswers().size());
        return question;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveQuestion(@RequestBody Question question, @RequestHeader String token) throws StackOverflowException {

        questionServiceImpl.createQuestions(question,token);
    }

    @PostMapping("/{Id}/Like")
    public void saveQuestion(@PathVariable String Id, @RequestHeader String token) throws StackOverflowException {

        questionServiceImpl.addLike(Id,token);
    }
}
