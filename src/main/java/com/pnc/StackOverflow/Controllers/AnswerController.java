package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.Answer;
import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Security.JWTHandler;
import com.pnc.StackOverflow.Service.AnswerService;
import com.pnc.StackOverflow.Service.QuestionServiceImpl;
import com.pnc.StackOverflow.Service.SequenceGeneratorService;
import com.pnc.StackOverflow.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("StackOverflow/Answers")
public class AnswerController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    UserServiceImpl userServiceimpl;
    @Autowired
    JWTHandler jwtHandler;

    @GetMapping("/{questionId}")
    public List<Answer> getAll(@PathVariable String questionId,@RequestHeader String token) {
        Optional<Question> question  =  answerService.getAnswers(questionId);
        if(question.isPresent()) {
            Question newQuestion = question.get();
                return newQuestion.getAnswers();

        }
        else
        {
            return null;
        }
    }

    @PostMapping("/{questionId}/addAnswer")
    public void addAnswer(@PathVariable String questionId , @RequestBody Answer answer, @RequestHeader String token) throws StackOverflowException {

        Optional<Question> question = questionServiceImpl.getQuestion(questionId,token);

        if(question.isPresent()){
            Question newQuestion = question.get();
            answer.setId(sequenceGeneratorService.generateSequence(Answer.SEQUENCE_NAME));
            if(newQuestion.getAnswers() != null)
                newQuestion.getAnswers().add(answer);
            else
            {
                List<Answer> newansList = new ArrayList<>();
                newansList.add(answer);
                newQuestion.setAnswers(newansList);
            }
            if(answer.getUser() == null) {
                Optional<User> user = userServiceimpl.findUserByUserId(jwtHandler.getUsernameFromToken(token));
                if (user.isPresent()) {
                    User userinfo = user.get();
                    System.out.println(user.toString());
                    answer.setUser(userinfo);

                } else {
                    throw new StackOverflowException(1014, "User not found");
                }
            }
            questionServiceImpl.createQuestions(newQuestion,token);
        }


    }

    @PostMapping("/{questionId}/{answerId}/answerUpdate")
    public void addAnswer(@PathVariable String questionId ,@PathVariable long answerId, @RequestBody Answer answerin,@RequestHeader String token) throws StackOverflowException {

        Optional<Question> question = questionServiceImpl.getQuestion(questionId,token);


        if(question.isPresent()) {
            Question newQuestion = question.get();
            List<Answer> answerList = newQuestion.getAnswers();
            Optional<Answer> answer = answerList.stream().filter(A -> A.getId() == answerId).findFirst();
            if (answer.isPresent()) {
                Answer newAnswer = answer.get();
                newAnswer.setAnswer(answerin.getAnswer());
                questionServiceImpl.createQuestions(newQuestion,token);
            }
        }


    }

}
