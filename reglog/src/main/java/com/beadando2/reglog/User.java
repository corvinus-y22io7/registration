package com.beadando2.reglog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
   private String fullname;
    @NotNull
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }


    public User(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public User(long id, String email) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
