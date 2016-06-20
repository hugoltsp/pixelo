package com.teles.yore.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.teles.yore.api")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
