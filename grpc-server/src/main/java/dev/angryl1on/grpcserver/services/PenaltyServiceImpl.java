package dev.angryl1on.grpcserver.services;

import dev.angryl1on.grpc.penalty.PenaltyGrpc;
import dev.angryl1on.grpc.penalty.PenaltyServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PenaltyServiceImpl extends PenaltyGrpc.PenaltyImplBase {

    @Override
    public void calculatePenalty(PenaltyServiceProto.CalculatePenaltyRequest request,
                                 StreamObserver<PenaltyServiceProto.CalculatePenaltyResponse> responseObserver) {
        LocalDate dueDate = LocalDate.parse(request.getDueDate());
        LocalDate returnDate = LocalDate.parse(request.getReturnDate());

        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        double penaltyAmount = daysLate > 0 ? daysLate * 50 : 0;

        PenaltyServiceProto.CalculatePenaltyResponse response = PenaltyServiceProto.CalculatePenaltyResponse.newBuilder()
                .setPenaltyAmount(penaltyAmount)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
