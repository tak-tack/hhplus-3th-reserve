package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    String reservationPayment(Integer userId, Integer seatPrice);
    List<PaymentResponseDTO> userPaymentFind(Integer userId);
    List<PaymentResponseDTO> userPaymentCharge(PaymentRequestDTO paymentRequestDTO);

}