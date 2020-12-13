package com.timelog.timelog.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

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

    public String email;

    public String password;


}