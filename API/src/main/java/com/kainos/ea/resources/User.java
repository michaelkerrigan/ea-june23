package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;

public class User {

    // VARIABLES
    private String username;
    private String password;
    private String role;

    // CONSTRUCTOR
//    public User(String username, String password, String role) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }

    // GETTER AND SETTERS
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // JSON OBJECT
    @JsonCreator
    public User(@JsonProperty("username") String username,
                    @JsonProperty("password") String password,
                    @JsonProperty("role") String role
    )
    {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
    }

}
