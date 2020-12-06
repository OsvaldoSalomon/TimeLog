package com.timelog.timelog.repositories;

import com.timelog.timelog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'_id': {'$in':?0}}")
    Optional<List<User>> findByIdList(List<String> userList);

    Page<User> findAllBy(TextCriteria criteria, Pageable pageable);

}