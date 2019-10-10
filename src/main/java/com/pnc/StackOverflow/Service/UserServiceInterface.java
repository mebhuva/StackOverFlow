package com.pnc.StackOverflow.Service;


import com.pnc.StackOverflow.Entities.User;

public interface UserServiceInterface {
    public boolean login(String email, String password) throws Exception;
    public boolean register(User user) throws Exception;
    public User findByEmail(String email);
    public void update(User user);
    public User findByUserName(String username);
    //public  User getOne(int uid);
    //public void delete(User user);
}
