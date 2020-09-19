package com.example.mysql;

import com.example.mysql.controllers.MainController;
import com.example.mysql.models.User;
import com.example.mysql.repositories.UserRepository;
import com.example.mysql.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing the controller
 * These tests are not meant to be comprehensive tests for
 * service layer.
 */
@WebMvcTest(MainController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class MainControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    MainController controller;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        userService = new UserService(userRepository);
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getShouldReturnUsers() throws Exception {

        User user1 = new User() {{
            setId(1);
            setName("testName");
            setEmail("1@test.com");
        }};

        User user2 = new User() {{
            setId(2);
            setName("testNameTwo");
            setEmail("2@test.com");
        }};

        ArrayList<User> users = new ArrayList<>(List.of(user1, user2));

        when(userService.findAll()).thenReturn(users);

        this.mockMvc.perform(get("/")).andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("testName")));

        verify(userService).findAll();
    }

    @Test
    public void getShouldReturnProperException() throws Exception {

        when(userService.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/")).andExpect(status().isNoContent());
    }

    @Test
    public void addShouldAddAndReturnUser() throws Exception {

        User user = new User() {{
            setId(1);
            setName("test");
            setEmail("test@test.com");
        }};

        when(userService.addUser(user)).thenReturn(user);

        String jsonUser = new ObjectMapper().writeValueAsString(user);

        this.mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().isCreated());
    }
}
