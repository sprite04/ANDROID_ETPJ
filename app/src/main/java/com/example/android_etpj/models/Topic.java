package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Topic implements Serializable {
    @SerializedName("TopicID")
    private int topicID;

    @SerializedName("TopicName")
    private String topicName;

    public Topic() {
    }

    public Topic(int topicID, String topicName) {
        this.topicID = topicID;
        this.topicName = topicName;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return topicName;
    }
}
