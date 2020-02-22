package com.example.mysql.repositories;

import com.example.mysql.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
