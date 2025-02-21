package dev.angryl1on.grpcserver.services;

import dev.angryl1on.grpc.billing.BillingServiceGrpc;
import dev.angryl1on.grpc.billing.CalculateFeeRequest;
import dev.angryl1on.grpc.billing.CalculateFeeResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Пример сервиса, который считает штраф.
 * Реализация очень упрощенная:
 * - Считаем, что если returnDate позже dueDate, то
 *   штраф = (количество просроченных дней) * 50.0 (условная ставка).
 */
@Service
public class BillingServiceImpl extends BillingServiceGrpc.BillingServiceImplBase {

    @Override
    public void calculateFee(CalculateFeeRequest request, StreamObserver<CalculateFeeResponse> responseObserver) {
        try {
            // Пример простой логики:
            // 1) Если нет returnDate, тогда штраф = 0 (книга ещё не возвращена).
            // 2) Если returnDate <= dueDate — штраф = 0.
            // 3) Если returnDate > dueDate, штраф = кол-во просроченных дней * 5.

            String dueDateStr = request.getDueDate();      // Дата, до которой нужно было вернуть
            String returnDateStr = request.getReturnDate(); // Дата фактического возврата

            double fee = 0.0;

            if (!returnDateStr.isEmpty() && !dueDateStr.isEmpty()) {
                LocalDate dueDate = LocalDate.parse(dueDateStr);
                LocalDate returnDate = LocalDate.parse(returnDateStr);

                if (returnDate.isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
                    fee = daysOverdue * 50.0; // условно 50 за день просрочки
                }
            }

            CalculateFeeResponse response = CalculateFeeResponse.newBuilder()
                    .setFee(fee)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
