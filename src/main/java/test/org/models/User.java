package test.org.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String login;
    private String password;
    private String lastName;
    private String firstName;
    private String surName;
    private String birthDate;
    private String sex;
    private String email;
    private String phone;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }
}
