package marxbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @EnableJpaRepositories("marxbank.backend.repository")
@EntityScan("marxbank.model")
@SpringBootApplication
public class Backend {

  public static void main(String... args) {
    SpringApplication.run(Backend.class, args);
  }
}

