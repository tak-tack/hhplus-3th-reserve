package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.PaymentService;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    // 유저 잔액 조회
    public List<PaymentResponseDTO> userPaymentSelect(Integer userId)
    {
        return paymentService.userPaymentFind(userId);
    } // 잔액조회
    // 유저 잔액 충전 후 조회
    public List<PaymentResponseDTO> userPaymentCharge(PaymentRequestDTO paymentRequestDTO)
    {
        return paymentService.userPaymentCharge(paymentRequestDTO);
    } // 잔액 충전


}
