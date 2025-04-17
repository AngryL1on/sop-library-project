package dev.angryl1on.grpcserver.configs;

import dev.angryl1on.grpcserver.services.PenaltyServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcConfiguration implements CommandLineRunner {
    private final PenaltyServiceImpl penaltyService;

    @Autowired
    public GrpcConfiguration(PenaltyServiceImpl penaltyService) {
        this.penaltyService = penaltyService;
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService(penaltyService)
                .build();

        server.start();
        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
}
