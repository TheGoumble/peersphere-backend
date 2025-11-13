package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"app", "controller", "config", "service", "dao", "model", "dto", "util"})
public class PeerSphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeerSphereApplication.class, args);
    }
}

