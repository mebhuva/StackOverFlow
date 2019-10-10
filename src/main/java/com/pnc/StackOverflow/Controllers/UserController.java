package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.ResponseMessage;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Models.LoginRequest;
import com.pnc.StackOverflow.Service.UserServiceImpl;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/StackOverflow")
public class UserController<UserService> {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    @Autowired
    UserServiceImpl userService;
    public User findUser(String email)
    {
        return userService.findByEmail(email);
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            LOGGER.info("LOGIN: User has entered Email and Password");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token",userService.login(loginRequest));
            LOGGER.info("LOGIN: Successful");
            return new ResponseEntity(responseHeaders,HttpStatus.OK);
        }catch(StackOverflowException e){
            LOGGER.info("LOGIN: Error invalid email and password combination");
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }




    }

    @PostMapping(value = "user")
    public ResponseEntity register(@RequestBody User user)
    {
        System.out.println(user.toString());
        try {
            LOGGER.info("Post(start): Register form has been submitted");
            userService.register(user);
            LOGGER.info(String.format("Post(end): User is registered with UserId: %s", user.getId()) );
        }catch (StackOverflowException e){
            return new ResponseEntity(new ResponseMessage(e.getStatusCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value="user")
    public void updateInfo(@RequestBody User user)
    {
        userService.update(user);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<List<ResponseMessage>> handleException(MethodArgumentNotValidException ex) {
//// Loop through FieldErrors in ex.getBindingResult();
//
//// return *YourErrorReponse* filled using *fieldErrors*
//    }
}
