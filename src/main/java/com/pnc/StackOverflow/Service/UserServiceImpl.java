package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Interceptors.PasswordHashing;
import com.pnc.StackOverflow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;



    byte[] salt = PasswordHashing.getSalt();

    public UserServiceImpl() throws NoSuchAlgorithmException {
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        try {
            User user = userRepository.findByEmail(email);
            //String hashedPassword=get_SHA_256_SecurePassword(password,salt);
            String hashedPassword=PasswordHashing.get_SHA_256_SecurePassword(password,salt);
            if(user.getPassword().equals(hashedPassword) && user.isActive()==true)
            {
                return true;
            }
            else
            {
                throw new StackOverflowException(1001,"Incorrect Credentials");
            }
        }
        catch(NullPointerException npe) {
            throw new StackOverflowException(1000,"user not found");
        }

    }

    @Override
    public boolean register(User user) throws Exception{
        String passwordToHash = user.getPassword();

        if(userRepository.findByEmail(user.getEmail())==null)
        {
            if(userRepository.findByUserName(user.getUserName())==null) {
                user.setActive(true);
                user.setPassword(PasswordHashing.get_SHA_256_SecurePassword(passwordToHash,salt));
                userRepository.save(user);
                return true;
            }
            else
            {
                throw new StackOverflowException(1003, "UserName already Exists");
            }
        }
        else
        {
            throw new StackOverflowException(1002, "Email already Exists");

        }

    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);

    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

//    @Override
//    public User getOne(int uid) {
//        return userRepository.getOne(uid);
//    }
}
