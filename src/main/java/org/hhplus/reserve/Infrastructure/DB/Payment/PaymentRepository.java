package org.hhplus.reserve.Infrastructure.DB.Payment;

import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository {
    Integer findUserAmountByUserId(UUID userId);
    List<PaymentDomain> findUserByUserId(UUID userId);
    void update(Integer paymentAmount,UUID userId);
    void register(UUID userId, Integer paymentAmount);

}
