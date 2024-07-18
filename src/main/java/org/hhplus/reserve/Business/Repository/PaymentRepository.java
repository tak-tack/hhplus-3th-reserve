package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;

import java.util.List;

public interface PaymentRepository {
    Integer findUserAmountByUserId(Integer userId);
    List<PaymentDomain> findUserByUserId(Integer userId);
    void update(Integer paymentAmount,Integer userId);
    void register(Integer userId, Integer paymentAmount);

}
