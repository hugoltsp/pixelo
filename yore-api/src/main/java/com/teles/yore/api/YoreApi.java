package com.teles.yore.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.teles.yore.api.verticle.v1.YoreImageVerticle;

import io.vertx.core.Vertx;

@SpringBootApplication
@ComponentScan("com.teles.yore.api")
public class YoreApi {

	private static final Logger log = LoggerFactory.getLogger(YoreApi.class);
	
	@Inject
	private YoreImageVerticle imageResource;

	public static void main(String[] args) {
		SpringApplication.run(YoreApi.class, args);
	}

	@PostConstruct
	public void init() {
		log.info("Initializing Yore Rest Api..");
		Vertx.vertx().deployVerticle(this.imageResource);
	}

}