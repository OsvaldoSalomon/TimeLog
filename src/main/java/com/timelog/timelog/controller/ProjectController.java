package com.timelog.timelog.controller;

import com.timelog.timelog.exceptions.ProjectNotFoundException;
import com.timelog.timelog.models.Project;
import com.timelog.timelog.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(PROJECTS_PATH)
    public @ResponseBody ResponseEntity<Project> addProject(@RequestBody Project project) {
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
