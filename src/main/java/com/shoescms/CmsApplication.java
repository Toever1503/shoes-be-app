package com.shoescms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CmsApplication {

	public static void main(String[] args) {
		System.setProperty("cusTomEnv.hello_work", "124");
		SpringApplication.run(CmsApplication.class, args);
	}

}
