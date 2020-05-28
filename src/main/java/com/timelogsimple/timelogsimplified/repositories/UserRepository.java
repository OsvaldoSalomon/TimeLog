package com.timelogsimple.timelogsimplified.repositories;

import com.timelogsimple.timelogsimplified.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {


    @Query("{'userList': {'$in':?0}}")
    List<User> findByUserList(Set<String> userList);
}
