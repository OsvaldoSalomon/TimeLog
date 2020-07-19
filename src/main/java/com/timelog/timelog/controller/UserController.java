package com.timelog.timelog.controller;

import com.google.common.collect.Lists;
import com.timelog.timelog.exceptions.CompanyNotFoundException;
import com.timelog.timelog.exceptions.CompanyPageParameterException;
import com.timelog.timelog.exceptions.UserNotFoundException;
import com.timelog.timelog.exceptions.UserPageParameterException;
import com.timelog.timelog.models.Company;
import com.timelog.timelog.models.User;
import com.timelog.timelog.repositories.UserRepository;
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
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(USERS_PATH)
    public ResponseEntity<List<User>> getUserList(
            @RequestParam(value = "userList", required = false) Set<String> requestedUserList,
            @RequestParam(name = "page", required = false/*, defaultValue = "0"*/) Integer page,
            @RequestParam(name = "size", required = false/*, defaultValue = "2"*/) Integer size) {

        List<User> userList;

        if (requestedUserList == null || requestedUserList.isEmpty()) {

            userList = getPagedUserList(page, size);
        } else {

            userList = getFilteredUserList(requestedUserList);
        }

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    private List<User> getFilteredUserList(Set<String> requestedUserList) {

        List<User>userList;
        Optional<List<User>> optionalList = userRepository.findByIdList(requestedUserList);
        if (!optionalList.isPresent()) {

            throw new UserNotFoundException("");
        }
        userList = optionalList.get();
        return userList;
    }

    private List<User> getPagedUserList(Integer page, Integer size) {

        if (page == null ^ size == null) {
            throw new UserPageParameterException();
        }

        List<User> userList;
        if (page == null /*&& size == null*/)  {

            userList = userRepository.findAll();
        } else {

            Pageable pageable = PageRequest.of(page, size);
            Page<User> requestedPage = userRepository.findAll(pageable);
            userList = Lists.newArrayList(requestedPage);
        }
        return userList;
    }

    @GetMapping(USERS_PATH + "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {

        Optional<User> optionalResponse = userRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }

    @PostMapping(USERS_PATH)
    public @ResponseBody ResponseEntity<User> addUser(@Validated @RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping(USERS_PATH + "/{id}")
    public void deleteUser(@PathVariable("id") String id) {

        Optional<User> optionalResponse = userRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @PutMapping(USERS_PATH + "/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable("id") String id) {

        Optional<User> optionalResponse = userRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new UserNotFoundException(id);
        }
        user.id = id;
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}