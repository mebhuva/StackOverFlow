package com.pnc.StackOverflow.Entities;
import com.pnc.StackOverflow.Event.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document(collection="Answer")
@TypeAlias("Answer")
public class Answer{

    @Transient
    public static final String SEQUENCE_NAME = "My_sequence";
    @Id
    private long id;
    private String answer;
    private List<User> Likes;
    private List<Comment> comments;

    private User user;

    public List<User> getLikes() {
        return Likes;
    }

    public void setLikes(List<User> likes) {
        Likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
