package io.github.felipesilva15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

    @Value("${application.name}")
    public String applicationName;

    @GetMapping("/hello")
    public String hello() {
        return applicationName;
    }
}