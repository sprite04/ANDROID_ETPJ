package com.example.android_etpj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Admin implements Serializable {
    @SerializedName("UserName")
    private String username;

    @SerializedName("Name")
    private String name;

    @SerializedName("Email")
    private String email;

    public Admin() {
    }

    public Admin(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
