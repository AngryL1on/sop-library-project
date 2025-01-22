package dev.angryl1on.library.core.listeners;

import dev.angryl1on.library.core.exceptions.BorrowingNotFoundException;
import dev.angryl1on.library.core.models.dtos.mb.FeeCalculationDTO;
import dev.angryl1on.library.core.models.entity.Borrowing;
import dev.angryl1on.library.core.repositories.BorrowingRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.angryl1on.library.core.configs.RabbitMQConfig.FEE_CALCULATION_QUEUE;

@Service
public class FeeListener {
    private final BorrowingRepository borrowingRepository;

    @Autowired
    public FeeListener(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    @RabbitListener(queues = FEE_CALCULATION_QUEUE)
    public void updateFee(FeeCalculationDTO feeCalculationDTO) {
        UUID borrowingId = feeCalculationDTO.getBorrowingId();
        double fee = feeCalculationDTO.getFee();

        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new BorrowingNotFoundException(borrowingId));

        borrowing.setFee(fee);
        borrowingRepository.save(borrowing);
    }
}
