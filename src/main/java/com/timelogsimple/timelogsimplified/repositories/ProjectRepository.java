package com.timelogsimple.timelogsimplified.repositories;


import com.timelogsimple.timelogsimplified.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectRepository extends MongoRepository<Project, String> {

    @Query("{'_id': {'$in':?0}}")
    Optional<List<Project>> findByIdList(Set<String> projectList);
}
