package com.timelogsimple.timelogsimplified.repositories;

import com.timelogsimple.timelogsimplified.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
