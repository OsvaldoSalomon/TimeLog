package com.timelogsimple.timelogsimplified.controller;

import com.timelogsimple.timelogsimplified.ProjectNotFoundException;
import com.timelogsimple.timelogsimplified.models.Project;
import com.timelogsimple.timelogsimplified.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.timelogsimple.timelogsimplified.constants.TimeLogConstants.*;

@RestController
@RequestMapping(TIME_LOG_V1_PATH)
public class ProjectController {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping(PROJECTS_PATH)
    public ResponseEntity<List<Project>> getProjectList(@RequestParam(required = false)
                                                                Set<String> projectList) {

        if (projectList == null || projectList.isEmpty()) {

            return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK);

        } else {

            Optional<List<Project>> optionalList = projectRepository.findByIdList(projectList);
            if (!optionalList.isPresent()) {

                throw new ProjectNotFoundException("");
            }
            return new ResponseEntity<>(optionalList.get(), HttpStatus.OK);
        }
    }

    @GetMapping(PROJECTS_PATH + "/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") String id) {

        Optional<Project> optionalResponse = projectRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new ProjectNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }

}
