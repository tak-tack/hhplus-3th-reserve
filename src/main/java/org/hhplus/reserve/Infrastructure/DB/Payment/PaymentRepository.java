package org.hhplus.reserve.Infrastructure.DB.Payment;

import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository {
    Integer findUserAmountByUserId(UUID userUuid);
    List<PaymentDomain> findUserByUserId(UUID userUuid);
    void update(Integer paymentAmount,UUID userUuid);
    void register(UUID userUuid, Integer paymentAmount);

}
