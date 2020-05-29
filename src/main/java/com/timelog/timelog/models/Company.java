package com.timelog.timelog.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("companies")
public class Company {

    @Id
    public String id;
    public String name;
    public List<String> projectList = new ArrayList<>();
    public List<String> userList = new ArrayList<>();
}
