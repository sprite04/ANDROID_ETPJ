package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    @SerializedName("QuestionID")
    private int questionID;

    @SerializedName("topicID")
    private int topicID;

    @SerializedName("QuestionContent")
    private String questionContent;

    @SerializedName("IsDeleted")
    private boolean isDeleted;

    @SerializedName("Feedbacks")
    private List<Feedback> feedbacks;

    public Question() {
    }

    public Question(int topicID, String questionContent) {
        this.topicID = topicID;
        this.questionContent = questionContent;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    @Override
    public String toString() {
        return questionContent ;
    }
}
