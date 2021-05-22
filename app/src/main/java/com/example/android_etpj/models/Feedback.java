package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Feedback implements Serializable {
    @SerializedName("FeedbackID")
    private int feedbackID;

    @SerializedName("Title")
    private String title;

    @SerializedName("AdminID")
    private String adminID;

    @SerializedName("isDeleted")
    private boolean IsDeleted;

    @SerializedName("TypeFeedbackID")
    private int typeFeedbackID;

    @SerializedName("Admin")
    private Admin admin;

    @SerializedName("TypeFeedback")
    private TypeFeedback typeFeedback;

    public Feedback(String title, String adminID, int typeFeedbackID) {
        this.title = title;
        this.adminID = adminID;
        this.IsDeleted=false;
        this.typeFeedbackID = typeFeedbackID;
    }

    public Feedback(int feedbackID, String title, String adminID, int typeFeedbackID) {
        this.feedbackID = feedbackID;
        this.title = title;
        this.adminID = adminID;
        this.typeFeedbackID = typeFeedbackID;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public int getTypeFeedbackID() {
        return typeFeedbackID;
    }

    public void setTypeFeedbackID(int typeFeedbackID) {
        this.typeFeedbackID = typeFeedbackID;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                ", title='" + title + '\'' +
                ", adminID='" + adminID + '\'' +
                ", IsDeleted=" + IsDeleted +
                ", typeFeedbackID=" + typeFeedbackID +
                ", admin=" + admin +
                ", typeFeedback=" + typeFeedback +
                '}';
    }
}
