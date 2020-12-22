package com.timelog.timelog.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.HashSet;
import java.util.Set;

@Data
@Document("users")
public class User {

    @Id
    public String id;

    @TextIndexed
    public String firstName;

    @TextIndexed
    public String lastName;

    @TextScore
    public Float textScore;

    @TextIndexed
    public String username;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public String email;

    public String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}