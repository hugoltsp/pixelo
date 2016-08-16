package com.teles.yore.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.teles.yore")
public class YoreApp {

	public static void main(String[] args) {
		SpringApplication.run(YoreApp.class, args);
	}

}
