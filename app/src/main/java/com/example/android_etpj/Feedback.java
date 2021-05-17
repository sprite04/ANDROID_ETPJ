package com.example.android_etpj;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String feedbackTitle;
    private String courseName;

    public Feedback(String feedbackTitle, String courseName) {
        this.feedbackTitle = feedbackTitle;
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return feedbackTitle;
    }
}
