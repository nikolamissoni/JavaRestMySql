package com.example.mysql.controllers;

import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import com.example.mysql.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class.getName());

    //@Autowired
    //private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public MainController(UserService uService){
        LOGGER.info("*-*-* Inside MainController constructor");
        this.userService = uService;
    }

    @PostMapping(path="add")
    //public @ResponseBody String addNewUser(@RequestBody User user){
    public @ResponseBody
    ResponseEntity addNewUser(@RequestBody User user){

        LOGGER.info("*-*-* info Entering addNewUser method");
        LOGGER.debug("*-*-* debug Entering addNewUser method");

        var ret = userService.addUser(user);

        return ResponseEntity.ok(ret);
    }

    @GetMapping(produces = "application/json")
    public Iterable<User> getUsers(){

        LOGGER.info("*-*-* info Entering getUsers method");
        LOGGER.debug("*-*-* debug Entering getUsers method");

        return  userService.findAll();
    }

    @PutMapping(path="update")
    public User updateUser(@RequestBody User user){
        //var updateUser = userRepository.findById(user.getId());
        return (userService.save(user));
    }

    @DeleteMapping(path="delete/{userId}")
    public String deleteUser(@PathVariable int userId){

        Optional<User> userToDelete = userService.findById(userId);
        userToDelete.ifPresent(u -> userService.delete(u));
        return "Deleted user with id: " + userId;
    }
}
