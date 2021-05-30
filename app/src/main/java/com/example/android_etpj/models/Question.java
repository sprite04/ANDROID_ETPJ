package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Question implements Serializable {
    @SerializedName("QuestionID")
    private int questionID;

    @SerializedName("TopicID")
    private int topicID;

    @SerializedName("QuestionContent")
    private String questionContent;

    @SerializedName("IsDeleted")
    private boolean isDeleted;

    public Question(int questionID, int topicID, String questionContent, boolean isDeleted) {
        this.questionID = questionID;
        this.topicID = topicID;
        this.questionContent = questionContent;
        this.isDeleted = isDeleted;
    }

    public Question() {
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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @SerializedName("Topic")
    private Topic topic;

    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", topicID='" + topicID + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", isDeleted=" + isDeleted +
                ", topic=" + topic +
                '}';
    }
}
