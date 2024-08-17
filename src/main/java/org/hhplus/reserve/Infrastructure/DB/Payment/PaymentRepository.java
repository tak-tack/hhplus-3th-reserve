package org.hhplus.reserve.Infrastructure.DB.Payment;

import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;

import java.util.List;

public interface PaymentRepository {
    Integer findUserAmountByUserId(Integer userId);
    List<PaymentDomain> findUserByUserId(Integer userId);
    void update(Integer paymentAmount,Integer userId);
    void register(Integer userId, Integer paymentAmount);

}
