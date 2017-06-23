package de.vanhck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VanificationApplication {
	public static final String PATH_OF_FILE_DIR = "values/";


	public static void main(String[] args) {
		SpringApplication.run(VanificationApplication.class, args);
	}
}
