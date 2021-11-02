package marxbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("marxbank.model")
@SpringBootApplication
public class Backend {

  public static void main(String... args) {
    SpringApplication.run(Backend.class, args);
  }
}

