package com.bendeguz.usedcar;

import com.bendeguz.usedcar.config.RsaKeyConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class UsedCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsedCarApplication.class, args);
	}

}
