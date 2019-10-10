package com.pnc.StackOverflow.Repository;

import com.pnc.StackOverflow.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmail(String email);
    public User findByUserName(String username);
}
