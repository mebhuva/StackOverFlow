package com.pnc.StackOverflow.Security;

import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    @Autowired
    private JWTHandler jwtHandler;

    @Before("execution(* com.pnc.StackOverflow.Service.QuestionServiceImpl.*(..)) && args(token)")
    //@Before("execution(* com.pnc.StackOverflow.Service.AnswerService.*(..)) && args(token)")
    public void validateUser(JoinPoint joinPoint, String token) throws StackOverflowException {

        try{
            if(!jwtHandler.validate(token)){
                throw new StackOverflowException(1012,"Unauthorized User");
            }
        }
        catch(StackOverflowError e){
            throw e;
        }
        catch(Exception e){
            throw e;
        }
    }
}
