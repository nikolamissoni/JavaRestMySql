package com.example.mysql.controllers;

import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import com.example.mysql.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class.getName());

    private final UserService userService;

    @Autowired
    public MainController(UserService uService){

        LOGGER.debug("*-*-* Inside MainController constructor");
        this.userService = uService;
    }

    @PostMapping(path="add")
    //public @ResponseBody String addNewUser(@RequestBody User user){
    //public @ResponseBody
    ResponseEntity<User> addNewUser(@RequestBody User user){

        LOGGER.debug("*-*-* Entering addNewUser method");

        var ret = userService.addUser(user);

        //return ResponseEntity.ok(ret);
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable<User>> getUsers(){

        LOGGER.debug("*-*-* Entering getUsers method");
        var users = userService.findAll();
        if(StreamSupport.stream(users.spliterator(), false).count() == 0){
            return (ResponseEntity.noContent().build());
        }

        return  ResponseEntity.ok(users);
    }

    //updated comment just for test
    @PutMapping(path="update")
    public User updateUser(@RequestBody User user){
        //var updateUser = userRepository.findById(user.getId());
        return (userService.save(user));
    }

    @PostMapping(path="updateEmail")
    public ResponseEntity<User> updateUserEmail(@RequestBody User user) {

        LOGGER.debug("*-*-* Update email called");
        return (userService.saveEmail(user));

    }

    @DeleteMapping(path="delete/{userId}")
    public String deleteUser(@PathVariable int userId){

        Optional<User> userToDelete = userService.findById(userId);
        userToDelete.ifPresent(u -> userService.delete(u));
        return "Deleted user with id: " + userId;
    }
}
