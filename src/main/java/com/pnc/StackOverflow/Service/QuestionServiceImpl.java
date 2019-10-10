package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.Question;
import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Repository.QuestionRepository;
import com.pnc.StackOverflow.Repository.UserRepository;
import com.pnc.StackOverflow.Security.JWTHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionServiceInterface {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    JWTHandler jwtHandler;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Question> getQuestions(String token) throws StackOverflowException { return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestion(String Id ,String token) throws StackOverflowException  {
        return questionRepository.findById(Id);
    }



    @Override
    public void createQuestions(Question question, String token) throws StackOverflowException  {
        if(jwtHandler.getUsernameFromToken(token) != null){
            if(question.getUser() == null) {
                Optional<User> user = userRepository.findById(jwtHandler.getUsernameFromToken(token));
                if (user.isPresent()) {
                    User userinfo = user.get();
                    System.out.println(user.toString());
                    question.setUser(userinfo);

                } else {
                    throw new StackOverflowException(1014, "User not found");
                }
            }

            questionRepository.save(question);
        }
        else{
            throw new  StackOverflowException(1010,"User is not Authorized");
        }

    }

    @Override
    public void addLike(String questionId,String token) throws StackOverflowException {

        Optional<Question> question = getQuestion(questionId,token);
        Optional<User> user = userRepository.findById(jwtHandler.getUsernameFromToken(token));
        if(question.isPresent()){
            Question newQuestion = question.get();
            if(newQuestion.getLikes() != null) {

                if (user.isPresent()) {
                    User userinfo = user.get();
                    System.out.println(user.toString());
                    if(!(newQuestion.getLikes().stream().filter(o -> o.getEmail().equals(userinfo.getEmail())).findFirst().isPresent()))
                        newQuestion.getLikes().add(userinfo);

                } else {
                    throw new StackOverflowException(1014, "User not found");
                }

            }
            else
            {
                List<User> likes = new ArrayList<>();
                if (user.isPresent()) {
                    User userinfo = user.get();
                    System.out.println(user.toString());
                    likes.add(userinfo);
                    newQuestion.setLikes(likes);

                } else {
                    throw new StackOverflowException(1014, "User not found");
                }

            }
            createQuestions(newQuestion,token);
        }

    }


}
