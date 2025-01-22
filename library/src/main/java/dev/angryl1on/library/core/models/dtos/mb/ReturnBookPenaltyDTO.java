package dev.angryl1on.library.core.models.dtos.mb;

import java.time.LocalDate;
import java.util.UUID;

public class ReturnBookPenaltyDTO {
    private LocalDate dueDate;
    private LocalDate returnDate;
    private UUID borrowingId; // ID заимствования

    public ReturnBookPenaltyDTO(LocalDate dueDate, LocalDate returnDate, UUID borrowingId) {
        this.dueDate = dueDate != null ? dueDate : LocalDate.now(); // Защита от null
        this.returnDate = returnDate;
        this.borrowingId = borrowingId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public UUID getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(UUID borrowingId) {
        this.borrowingId = borrowingId;
    }
}
