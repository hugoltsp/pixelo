package com.teles.pixelo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.teles.pixelo")
public class PixeloApp {

	public static void main(String[] args) {
		SpringApplication.run(PixeloApp.class, args);
	}

}
