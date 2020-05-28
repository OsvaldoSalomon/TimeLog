package com.timelogsimple.timelogsimplified.controller;

import com.timelogsimple.timelogsimplified.models.Company;
import com.timelogsimple.timelogsimplified.models.Project;
import com.timelogsimple.timelogsimplified.models.User;
import com.timelogsimple.timelogsimplified.repositories.CompanyRepository;
import com.timelogsimple.timelogsimplified.repositories.ProjectRepository;
import com.timelogsimple.timelogsimplified.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/timelog2")
public class TimeLogController {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // *************** Methods for Companies ****************

//    List<Company> getList = ;

    @GetMapping("/companies")
    public List<Company> getAllApplications() {
        return companyRepository.findAll();
    }

    @GetMapping("/companies/{id}")
    public Optional<Company> getApplicationById(@PathVariable("id") String id) {
        return companyRepository.findById(id);
    }

    @GetMapping("/companies/")
    public ResponseEntity<List<User>> getUserListWithFilter(@RequestParam(required = false)Set<String> userList) {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    // *************** Methods for Projects ****************

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/{id}")
    public Optional<Project> getProjectById(@PathVariable("id") String id) {
        return projectRepository.findById(id);
    }

    // *************** Methods for Users ****************
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return userRepository.findById(id);
    }
}
