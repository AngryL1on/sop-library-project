package dev.angryl1on.grpcclient.services;

import dev.angryl1on.grpc.penalty.PenaltyGrpc;
import dev.angryl1on.grpc.penalty.PenaltyServiceProto;
import dev.angryl1on.grpcclient.models.dtos.FeeCalculationDTO;
import dev.angryl1on.grpcclient.models.dtos.ReturnBookPenaltyDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static dev.angryl1on.grpcclient.configs.RabbitMQConfig.FEE_CALCULATION_QUEUE;
import static dev.angryl1on.grpcclient.configs.RabbitMQConfig.RETURN_BOOK_PENALTY_QUEUE;

@Service
public class PenaltyCalculatorService {
    private final RabbitTemplate rabbitTemplate;
    private final PenaltyGrpc.PenaltyBlockingStub penaltyBlockingStub;

    public PenaltyCalculatorService(RabbitTemplate rabbitTemplate) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        this.penaltyBlockingStub = PenaltyGrpc.newBlockingStub(channel);
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RETURN_BOOK_PENALTY_QUEUE)
    public void calculatePenalty(ReturnBookPenaltyDTO penaltyDTO) {
        PenaltyServiceProto.CalculatePenaltyRequest request = PenaltyServiceProto.CalculatePenaltyRequest.newBuilder()
                .setDueDate(penaltyDTO.getDueDate().toString())
                .setReturnDate(penaltyDTO.getReturnDate().toString())
                .build();

        PenaltyServiceProto.CalculatePenaltyResponse response = penaltyBlockingStub.calculatePenalty(request);

        FeeCalculationDTO feeCalculationDTO = new FeeCalculationDTO(penaltyDTO.getBorrowingId(), response.getPenaltyAmount());
        rabbitTemplate.convertAndSend(FEE_CALCULATION_QUEUE, feeCalculationDTO);
    }
}
