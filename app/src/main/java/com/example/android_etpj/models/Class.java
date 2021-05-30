package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Class implements Serializable {
    @SerializedName("ClassID")
    private int classID;

    @SerializedName("ClassName")
    private String className;

    @SerializedName("Capacity")
    private int capacity;

    @SerializedName("StartTime")
    private Date startTime;

    @SerializedName("EndTime")
    private Date endTime;

    @SerializedName("IsDeleted")
    private boolean isDeleted;

    public Class() {
    }

//    public Class(int classID, String className, int capacity, Date startTime, Date endTime, boolean isDeleted) {
//        this.classID = classID;
//        this.className = className;
//        this.capacity = capacity;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.isDeleted = isDeleted;
//    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classID=" + classID +
                ", className='" + className + '\'' +
                ", capacity=" + capacity +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
