package io.changrea.restapiexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestapiexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestapiexampleApplication.class, args);
    }

}
