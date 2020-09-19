package com.example.mysql;

import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import com.example.mysql.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void SaveEMail() {

        User user = new User() {{
            setId(1);
            setName("testName");
            setEmail("1@test.com");
        }};

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        ResponseEntity<User> serviceResponse =  userService.saveEmail(user);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, serviceResponse.getStatusCode());
    }
}
