package com.timelog.timelog.controller;

import com.timelog.timelog.exceptions.ProjectNotFoundException;
import com.timelog.timelog.models.Project;
import com.timelog.timelog.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping(PROJECTS_PATH)
    public ResponseEntity<Map<String, Object>> getAllProjects(
            @RequestParam(required = false) String name,
            @RequestParam(required = false/*, defaultValue = "0"*/) Integer page,
            @RequestParam(required = false/*, defaultValue = "3"*/) Integer size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            Map<String, Object> responseAll = new HashMap<>();

            List<Project> projects;
            if (page == null) {
                projects = projectRepository.findAll();
                responseAll.put("projects", projects);
                return new ResponseEntity<>(responseAll, HttpStatus.OK);
            }

            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Project> pageTuts;
            if (name == null)
                pageTuts = projectRepository.findAll(pagingSort);
            else
                pageTuts = projectRepository.findByNameContaining(name, pagingSort);

            projects = pageTuts.getContent();

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalProjects", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public @ResponseBody
    ResponseEntity<Project> addProject(@Validated @RequestBody Project project) {
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