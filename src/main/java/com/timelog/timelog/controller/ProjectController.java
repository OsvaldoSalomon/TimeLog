package com.timelog.timelog.controller;

import com.google.common.collect.Lists;
import com.timelog.timelog.exceptions.ProjectNotFoundException;
import com.timelog.timelog.exceptions.ProjectPageParameterException;
import com.timelog.timelog.models.Project;
import com.timelog.timelog.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.timelog.timelog.constants.TimeLogConstants.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(TIME_LOG_V1_PATH)
public class ProjectController {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping(PROJECTS_PATH)
    public ResponseEntity<List<Project>> getProjectList(
            @RequestParam(value = "projectList", required = false) Set<String> requestedProjectList,
            @RequestParam(name = "page", required = false/*, defaultValue = "0"*/) Integer page,
            @RequestParam(name = "size", required = false/*, defaultValue = "2"*/) Integer size) {
        
        List<Project> projectList;

        if (requestedProjectList == null || requestedProjectList.isEmpty()) {
            
            projectList = getPagedProjectList(page, size);
        } else {

            projectList = getFilteredProjectList(requestedProjectList);
        }
        
        return new ResponseEntity<>(projectList, HttpStatus.OK); 
    }
    
    private List<Project> getFilteredProjectList(Set<String> requestedProjectList) {

        List<Project> projectList;
        Optional<List<Project>> optionalList = projectRepository.findByIdList(requestedProjectList);
        if (!optionalList.isPresent()) {

            throw new ProjectNotFoundException("");
        }
        projectList = optionalList.get();
        return projectList;
    }

    private List<Project> getPagedProjectList(Integer page, Integer size) {

        if (page == null ^ size == null) {
            throw new ProjectPageParameterException();
        }

        List<Project> projectList;
        if (page == null /*&& size == null*/)  {

            projectList = projectRepository.findAll();
        } else {

            Pageable pageable = PageRequest.of(page, size);
            Page<Project> requestedPage = projectRepository.findAll(pageable);
            projectList = Lists.newArrayList(requestedPage);
        }
        return projectList;
    }

    @GetMapping(PROJECTS_PATH + "/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") String id) {

        Optional<Project> optionalResponse = projectRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new ProjectNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }

    @PostMapping(PROJECTS_PATH)
    public @ResponseBody ResponseEntity<Project> addProject(@Validated @RequestBody Project project) {
        projectRepository.save(project);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @DeleteMapping(PROJECTS_PATH + "/{id}")
    public void deleteProject(@PathVariable("id") String id) {

        Optional<Project> optionalResponse = projectRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new ProjectNotFoundException(id);
        }
        projectRepository.deleteById(id);
    }

    @PutMapping(PROJECTS_PATH + "/{id}")
    public ResponseEntity<Project> updateProjectById(@RequestBody Project project, @PathVariable("id") String id) {

        Optional<Project> optionalResponse = projectRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new ProjectNotFoundException(id);
        }
        project.id = id;
        projectRepository.save(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

}