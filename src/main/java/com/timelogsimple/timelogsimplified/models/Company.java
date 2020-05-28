package com.timelogsimple.timelogsimplified.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("companies")
public class Company {

    public String id;
    public String name;
    public List<String> projectList = new ArrayList<>();
    public List<String> userList = new ArrayList<>();
}
