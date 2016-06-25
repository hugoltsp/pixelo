package com.teles.yore.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.yore.api.verticle.v1.YoreImageVerticle;

import io.vertx.core.Vertx;

@SpringBootApplication
@ComponentScan("com.teles.yore.api")
public class YoreApi {

	@Inject
	private YoreImageVerticle imageResource;

	public static void main(String[] args) {
		SpringApplication.run(YoreApi.class, args);
	}

	@PostConstruct
	public void init() {
		Vertx.vertx().deployVerticle(this.imageResource);
	}

}