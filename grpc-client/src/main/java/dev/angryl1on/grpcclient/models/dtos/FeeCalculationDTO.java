package dev.angryl1on.grpcclient.models.dtos;

import java.util.UUID;

public class FeeCalculationDTO {
    private UUID borrowingId;
    private double fee;

    public FeeCalculationDTO(UUID borrowingId, double fee) {
        this.borrowingId = borrowingId;
        this.fee = fee;
    }

    public UUID getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(UUID borrowingId) {
        this.borrowingId = borrowingId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
