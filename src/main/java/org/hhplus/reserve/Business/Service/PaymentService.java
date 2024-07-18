package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    String ReservationPayment(Integer userId, Integer seatPrice);
    List<PaymentResponseDTO> UserPaymentFind(Integer userId);
    List<PaymentResponseDTO> UserPaymentCharge(PaymentRequestDTO paymentRequestDTO);

}
