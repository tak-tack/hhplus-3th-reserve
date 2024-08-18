package org.hhplus.reserve.Business.Domain.Payment;

import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    void reservationPayment(Integer userId, Integer seatPrice);
    List<PaymentResponseDTO> userPaymentFind(Integer userId);
    List<PaymentResponseDTO> userPaymentCharge(PaymentRequestDTO paymentRequestDTO);

}