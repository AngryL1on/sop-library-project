package dev.angryl1on.grpcclient.services;

import dev.angryl1on.grpc.billing.BillingServiceGrpc;
import dev.angryl1on.grpc.billing.BillingServiceProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class BillingGrpcService {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingGrpcService() {
         ManagedChannel channel = ManagedChannelBuilder
                 .forAddress("localhost", 9090)
                 .usePlaintext()
                 .build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Вызывает удалённый метод calculateFee и возвращает рассчитанный штраф.
     */
    public double calculateOverdueFee(String borrowingId, String borrowDate, String dueDate, String returnDate) {
        BillingServiceProto.CalculateFeeRequest request = BillingServiceProto.CalculateFeeRequest.newBuilder()
                .setBorrowingId(borrowingId)
                .setBorrowDate(borrowDate != null ? borrowDate : "")
                .setDueDate(dueDate != null ? dueDate : "")
                .setReturnDate(returnDate != null ? returnDate : "")
                .build();

        BillingServiceProto.CalculateFeeResponse response = blockingStub.calculateFee(request);
        return response.getFee();
    }
}
