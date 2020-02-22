package com.example.mysql.services;

import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

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
}
