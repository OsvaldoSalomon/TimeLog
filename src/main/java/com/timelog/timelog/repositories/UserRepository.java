package com.timelog.timelog.repositories;

import com.timelog.timelog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'_id': {'$in':?0}}")
    Optional<List<User>> findByIdList(Set<String> userList);

    @Query("{ $text: { $search: ?0 } }")
    Page<User> searchUsers(String search, Pageable pageable);

    Page<User> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email, Pageable pageable);

    Page<User> findByFirstNameAndLastNameAndEmailAndId(String firstName, String lastName, String email, String id, Pageable pageable);

    Page<User> findByFirstName(String lastName, Pageable pageable);

}
