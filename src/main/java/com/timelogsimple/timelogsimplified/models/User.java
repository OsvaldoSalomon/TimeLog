package com.timelogsimple.timelogsimplified.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
}
