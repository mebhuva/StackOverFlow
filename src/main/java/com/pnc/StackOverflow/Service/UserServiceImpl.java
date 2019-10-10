package com.pnc.StackOverflow.Service;

import com.pnc.StackOverflow.Entities.User;
import com.pnc.StackOverflow.ExceptionHandling.StackOverflowException;
import com.pnc.StackOverflow.Interceptors.PasswordHashing;
import com.pnc.StackOverflow.Models.LoginRequest;
import com.pnc.StackOverflow.Repository.UserRepository;
import com.pnc.StackOverflow.Security.JWTHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    //Log4j
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private JWTHandler jwtHandler;


    byte[] salt = PasswordHashing.getSalt();

    public UserServiceImpl() throws NoSuchAlgorithmException {
    }

    @Override
    public String login(LoginRequest loginRequest) throws StackOverflowException {
        LOGGER.info("LOGIN: Request to login");
        User userEntity = userRepository.findByEmail(loginRequest.getEmail());
        String token = null;
        try {
            if (userEntity != null) {
                if (userEntity.getPassword().equals(toHexString(getSHA(loginRequest.getPassword())))) {
                    token = jwtHandler.createToken(userEntity.getId());
                }
            }
        } catch (Exception e) {
            LOGGER.info("Login: Failed to login the user");
            throw new StackOverflowException(1008, "User has entered invalid email or password");
        }
        return token;


    }

    @Override
    public void register(User user) throws StackOverflowException, NoSuchAlgorithmException {
        LOGGER.info("CREATE NEW USER: Starting process for creating new user.");
        if (userRepository.findByEmail(user.getEmail()) == null) {
            if (userRepository.findByUserName(user.getUserName()) == null) {
                user.setPassword(toHexString(getSHA(user.getPassword())));
                user.setActive(true);
                userRepository.save(user);
                LOGGER.info(String.format("CREATE NEW USER: New user %s has been made", user.getUserName()));
            } else {
                LOGGER.info("CREATE NEW USER: Exception display name chosen is already taken");
                throw new StackOverflowException(1003, "Display Name already exists");
            }
        } else {
            LOGGER.info("CREATE NEW USER: Exception email already exists");
            throw new StackOverflowException(1007, "Email already exists");
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


    @Override
    public void invalidateToken(String token) throws StackOverflowException {
        try{
            jwtHandler.revokeToken(token);
        }
        catch(Exception e){
            LOGGER.info("Logout: Failed to logout the user");
            throw new StackOverflowException(1009,"Unable to Logout");
        }
    }

    @Override
    public Optional<User> findUserByUserId(String userId) throws StackOverflowException {
        LOGGER.info("FIND USER BY USER ID");
        if(!userRepository.findById(userId).isPresent()){
            LOGGER.info("FIND USER BY ID: Error User does not exist");
            throw new StackOverflowException(1000,String.format("User with UserId: %s does not exist", userId));
        }
        else {
            LOGGER.info("FIND USER BY ID: User exists");
            return userRepository.findById(userId);
        }
    }


    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        System.out.println(input);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }

}
