package com.example.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlApplication.class, args);
	}

}

//docker run -p 33060:33060 -p 3306:3306 --name mysql-latest -e MYSQL_ROOT_HOST='%' -e MYSQL_ROOT_PASSWORD='pass' -d mysql:latest
