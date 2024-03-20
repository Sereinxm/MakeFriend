package com.example.backed;

import jdk.jfr.Enabled;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.backed.mapper")
@EnableScheduling
public class BackedApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackedApplication.class, args);
	}

}
