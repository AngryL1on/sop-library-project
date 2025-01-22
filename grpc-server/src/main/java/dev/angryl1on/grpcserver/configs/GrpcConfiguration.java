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

    /**
     * Constructor for injecting the {@link PenaltyServiceImpl}.
     *
     * @param penaltyService The service implementation to be used by the gRPC server.
     */
    @Autowired
    public GrpcConfiguration(PenaltyServiceImpl penaltyService) {
        this.penaltyService = penaltyService;
    }

    /**
     * Starts the gRPC server when the application begins running.
     *
     * <p>The server is configured to listen on port 8080 and register the provided
     * {@link PenaltyServiceImpl}. Once started, the server blocks the main
     * thread and continues to handle incoming gRPC requests until termination.</p>
     *
     * @param args Command-line arguments passed to the application (not used).
     * @throws Exception If an error occurs during server startup or execution.
     */
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
