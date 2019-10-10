package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.Answer;
import com.pnc.StackOverflow.Entities.Comment;
import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Service.QuestionServiceImpl;
import com.pnc.StackOverflow.Service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("StackOverflow/Comments")
public class CommentController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/{questionId}/{answerId}")
    public List<Comment> getAll(@PathVariable String questionId, @PathVariable Long answerId) {
        Optional<Question> question = questionServiceImpl.getQuestion(questionId);
        if (question.isPresent()) {
            Question newQuestion = question.get();
            List<Answer> answers = newQuestion.getAnswers();
            Optional<Answer> answer = answers.stream().filter(A -> A.getId() == answerId).findFirst();
            if (answer.isPresent()) {
                Answer newAnswer = answer.get();
                if (newAnswer.getComments() != null) {
                    return newAnswer.getComments();

                }
            }
        }
    return null;
    }

    @PostMapping("{questionId}/{answerId}/addCommentToAnswer")
    public void addCommentToAnswer(@PathVariable String questionId , @PathVariable Long answerId , @RequestBody Comment comment) {
        Optional<Question> question = questionServiceImpl.getQuestion(questionId);
        if (question.isPresent()) {
            Question newQuestion = question.get();
            List<Answer> answers = newQuestion.getAnswers();
            Optional<Answer> answer = answers.stream().filter(A -> A.getId() == answerId).findFirst();
            if (answer.isPresent()) {
                Answer newAnswer = answer.get();
                comment.setId(sequenceGeneratorService.generateSequence(Comment.SEQUENCE_NAME));
                if (newAnswer.getComments() != null) {
                    newAnswer.getComments().add(comment);

                } else {
                    List<Comment> comments = new ArrayList<>();
                    comments.add(comment);
                    newAnswer.setComments(comments);
                }
                newQuestion.setAnswers(answers);
                questionServiceImpl.createQuestions(newQuestion);
            }
        }
    }
    @PostMapping("{questionId}/addCommentToQuestion")
    public void addCommentToQuestion(@PathVariable String questionId , @RequestBody Comment comment) {
        Optional<Question> question = questionServiceImpl.getQuestion(questionId);
        if (question.isPresent()) {
            Question newQuestion = question.get();
            comment.setId(sequenceGeneratorService.generateSequence(Comment.SEQUENCE_NAME));
                if (newQuestion.getComments() != null) {
                    newQuestion.getComments().add(comment);

                } else {
                    List<Comment> comments = new ArrayList<>();
                    comments.add(comment);
                    newQuestion.setComments(comments);
                }
            questionServiceImpl.createQuestions(newQuestion);
        }
    }
}
