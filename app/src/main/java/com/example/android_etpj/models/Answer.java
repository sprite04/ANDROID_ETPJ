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

    @SerializedName("Module")
    private Module module ;

    @SerializedName("Trainee")
    private Trainee trainee ;

    @SerializedName("Class")
    private Class clss ;

    @SerializedName("Question")
    private Question question ;

    public Answer(int classID, int moduleID, String traineeID, int questionID, int value) {
        this.classID = classID;
        this.moduleID = moduleID;
        this.traineeID = traineeID;
        this.questionID = questionID;
        this.value = value;
    }

    public Answer() {
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public Class getClss() {
        return clss;
    }

    public void setClss(Class clss) {
        this.clss = clss;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "classID=" + classID +
                ", moduleID=" + moduleID +
                ", traineeID=" + traineeID +
                ", questionID=" + questionID +
                ", value=" + value +
                ", module=" + module +
                ", trainee=" + trainee +
                ", clss=" + clss +
                ", question=" + question +
                '}';
    }
}
