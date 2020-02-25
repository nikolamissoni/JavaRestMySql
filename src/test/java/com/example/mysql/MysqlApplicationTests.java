package com.example.mysql;

import com.example.mysql.controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class MysqlApplicationTests {

	@Autowired
	private MainController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
