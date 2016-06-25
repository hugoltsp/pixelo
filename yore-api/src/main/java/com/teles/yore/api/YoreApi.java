package com.teles.yore.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.yore.api.verticle.v1.YoreApiVerticle;

import io.vertx.core.Vertx;

@SpringBootApplication
@ComponentScan("com.teles.yore.api")
public class YoreApi {

	@Inject
	private YoreApiVerticle verticle;

	public static void main(String[] args) {
		SpringApplication.run(YoreApi.class, args);
	}

	@PostConstruct
	public void init() {
		Vertx.vertx().deployVerticle(this.verticle);
	}

}