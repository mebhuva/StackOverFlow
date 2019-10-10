package com.pnc.StackOverflow.Controllers;

import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.ResponseMessage;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Models.LoginRequest;
import com.pnc.StackOverflow.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/StackOverflow")
public class UserController<UserService> {
    @Autowired
    UserServiceImpl userService;
    public User findUser(String email)
    {
        return userService.findByEmail(email);
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            userService.login(loginRequest.getEmail(),loginRequest.getPassword());
        } catch(StackOverflowException ke) {
            return new ResponseEntity(new ResponseMessage(ke.getStatusCode(),ke.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping(value = "user")
    public ResponseEntity register(@RequestBody User user)
    {
        try
        {
            userService.register(user);
        }
        catch(StackOverflowException koe)
        {
            return new ResponseEntity(new ResponseMessage(koe.getStatusCode(),koe.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity(HttpStatus.OK);
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
