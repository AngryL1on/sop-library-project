package dev.angryl1on.grpcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Default class for start spring-application
 */
@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
    }

}
