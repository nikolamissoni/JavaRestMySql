package com.example.mysql.services;

import com.example.mysql.controllers.MainController;
import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User addUser(User user){

        return userRepository.save(user);
    }

    public Iterable<User> findAll() {

        return userRepository.findAll();
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public Optional<User> findById(int userId) {

        return userRepository.findById(userId);
    }

    public void delete(User user) {

        userRepository.delete(user);
    }

    public ResponseEntity<User> saveEmail(User user) {

        LOGGER.debug("*-*-* saveEmail");
        Optional<User> foundUser = userRepository.findById(user.getId());
        if(foundUser.isEmpty()) {
            LOGGER.debug("*-*-* foundUser optional is empty");
            return ResponseEntity.notFound().build();
        }

        if(!user.getEmail().contains("@")) {
            return ResponseEntity.unprocessableEntity().build();
        }

        LOGGER.debug("*-*-* saveEmail set user to some object");
        foundUser.get().setEmail(user.getEmail());
        return ResponseEntity.ok(userRepository.save(foundUser.get()));
    }
}
