package com.example.mysql;

import com.example.mysql.controllers.MainController;
import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import com.example.mysql.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
@TestPropertySource(locations="classpath:test.properties")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getShouldReturnUsers() throws Exception {

        User user1 = new User(){{
            setId(1);
            setName("testName");
            setEmail("1@test.com");
        }};

        User user2 = new User(){{
           setId(2);
           setName("testNameTwo");
           setEmail("2@test.com");
        }};

        ArrayList<User> users = new ArrayList<>(List.of(user1, user2));

        when(userService.findAll()).thenReturn(users);

        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("testName")));
    }

    @Test
    public void getShouldReturnProperException() throws Exception {

        when(userService.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/")).andExpect(status().isNoContent());
    }

    @Test
    public void addShouldAddAndReturnUser() throws Exception {

        User user = new User(){{
           setId(1);
           setName("test");
           setEmail("test@test.com");
        }};

        when(userService.addUser(user)).thenReturn(user);

        String jsonUser = new ObjectMapper().writeValueAsString(user);

        this.mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().isOk());
    }

    @Test
    public void userServiceFindAll(){

        UserService svc = new UserService(new UserRepository() {
            @Override
            public <S extends User> S save(S s) {
                return null;
            }

            @Override
            public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public Optional<User> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public Iterable<User> findAll() {
                ArrayList<User> users = new ArrayList<>();
                return users;
            }

            @Override
            public Iterable<User> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(User user) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> iterable) {

            }

            @Override
            public void deleteAll() {

            }
        });

        //var res = userService.findAll();
        var res = svc.findAll();

        Assertions.assertTrue(res != null);
        Assertions.assertTrue(res.getClass() == ArrayList.class);
    }
}
