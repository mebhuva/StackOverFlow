package com.pnc.StackOverflow.Repository;

import com.pnc.StackOverflow.Entities.Question;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository  extends MongoRepository<Question, String> {

}
