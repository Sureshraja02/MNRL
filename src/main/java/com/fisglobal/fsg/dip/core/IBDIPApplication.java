package com.fisglobal.fsg.dip.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableWebMvc
@ComponentScan({ "com.fisglobal" })
@EntityScan({ "com.fisglobal" })
@EnableJpaRepositories({ "com.fisglobal" })
@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
public class IBDIPApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IBDIPApplication.class, args);
	}

}
