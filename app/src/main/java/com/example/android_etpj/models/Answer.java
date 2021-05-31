package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {
    @SerializedName("ClassID")
    private int classID;

    @SerializedName("ModuleID")
    private int moduleID;

    @SerializedName("TraineeID")
    private String traineeID;

    @SerializedName("QuestionID")
    private int questionID;

    @SerializedName("Value")
    private int value;

    public Answer(int classID, int moduleID, String traineeID, int questionID, int value) {
        this.classID = classID;
        this.moduleID = moduleID;
        this.traineeID = traineeID;
        this.questionID = questionID;
        this.value = value;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(String traineeID) {
        this.traineeID = traineeID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
