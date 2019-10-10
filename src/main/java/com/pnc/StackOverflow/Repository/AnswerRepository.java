package com.pnc.StackOverflow.Repository;

import com.pnc.StackOverflow.Entities.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {}

