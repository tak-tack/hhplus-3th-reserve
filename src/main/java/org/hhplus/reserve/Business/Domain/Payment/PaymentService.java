package org.hhplus.reserve.Business.Domain.Payment;

import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    void reservationPayment(UUID userUuid, Integer seatPrice);
    List<PaymentResponseDTO> userPaymentFind(UUID userUuid);
    List<PaymentResponseDTO> userPaymentCharge(PaymentRequestDTO paymentRequestDTO);

}