package com.timelog.timelog.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("projects")
public class Project {
    public String id;
    public String name;
    public String company;
    public List<String> userList = new ArrayList<>();
}
