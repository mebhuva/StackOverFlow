package com.pnc.StackOverflow.Entities;
import com.pnc.StackOverflow.Event.CascadeSave;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document(collection="Questions")
@TypeAlias("Questions")
public class Question extends AbstractDocument {

    private int view;

    private int answerCount;

    private String title;

    private List<Answer> answers;

    private List<Comment> comments;

    public Question() {
    }

    public Question(int view, int answerCount, String title, List<Answer> answers) {
        this.view = view;
        this.answerCount = answerCount;
        this.title = title;
        this.answers = answers;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getView() == question.getView() &&
                getAnswerCount() == question.getAnswerCount() &&
                Objects.equals(getTitle(), question.getTitle()) &&
                Objects.equals(getAnswers(), question.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getView(), getAnswerCount(), getTitle(), getAnswers());
    }

    @Override
    public String toString() {
        return "Question{" +
                "view=" + view +
                ", answerCount=" + answerCount +
                ", title='" + title + '\'' +
                ", answers=" + answers +
                '}';
    }
}
