package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan(basePackages = "entity")
public class MeageaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeageaApplication.class, args);
	}
}
