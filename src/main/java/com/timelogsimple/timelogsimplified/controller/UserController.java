package com.timelogsimple.timelogsimplified.controller;

import com.timelogsimple.timelogsimplified.exceptions.UserNotFoundException;
import com.timelogsimple.timelogsimplified.models.User;
import com.timelogsimple.timelogsimplified.repositories.UserRepository;
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
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(USERS_PATH)
    public ResponseEntity<List<User>> getUserList(@RequestParam(required = false)
                                                                Set<String> userList) {

        if (userList == null || userList.isEmpty()) {

            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);

        } else {

            Optional<List<User>> optionalList = userRepository.findByIdList(userList);
            if (!optionalList.isPresent()) {

                throw new UserNotFoundException("");
            }
            return new ResponseEntity<>(optionalList.get(), HttpStatus.OK);
        }
    }

    @GetMapping(USERS_PATH + "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {

        Optional<User> optionalResponse = userRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }

}
