package org.hhplus.reserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Hhplus3thReserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hhplus3thReserveApplication.class, args);
	}

}
