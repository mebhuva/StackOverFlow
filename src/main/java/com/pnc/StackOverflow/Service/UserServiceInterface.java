package com.pnc.StackOverflow.Service;


import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Models.LoginRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserServiceInterface {
    public String login(LoginRequest loginRequest)  throws StackOverflowException;
    public void register(User user) throws StackOverflowException, NoSuchAlgorithmException;
    public User findByEmail(String email)throws StackOverflowException;
    public void update(User user)throws StackOverflowException;
    public User findByUserName(String username)throws StackOverflowException;
    public void invalidateToken(String token) throws StackOverflowException;
    public Optional<User> findUserByUserId(String userId) throws StackOverflowException;
    //public  User getOne(int uid);
    //public void delete(User user);
}
