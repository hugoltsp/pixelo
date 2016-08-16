package com.teles.pixelo.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.pixelo.api.verticle.PixeloApiVerticle;

import io.vertx.core.Vertx;

@SpringBootApplication
@ComponentScan("com.teles.pixelo.api")
public class PixeloApi {

	@Inject
	private PixeloApiVerticle verticle;

	public static void main(String[] args) {
		SpringApplication.run(PixeloApi.class, args);
	}

	@PostConstruct
	public void init() {
		Vertx.vertx().deployVerticle(this.verticle);
	}

}