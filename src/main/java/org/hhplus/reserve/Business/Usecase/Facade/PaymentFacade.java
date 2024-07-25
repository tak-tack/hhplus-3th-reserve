package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.PaymentService;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    public List<PaymentResponseDTO> UserPaymentSelect(Integer userId)
    {

        // 유저 잔액 조회
        return paymentService.UserPaymentFind(userId); // 잔액조회
    }

    public List<PaymentResponseDTO> UserPaymentCharge(PaymentRequestDTO paymentRequestDTO)
    {
        // 유저 잔액 충전 후 조회
        return paymentService.UserPaymentCharge(paymentRequestDTO); // 잔액 충전
    }

}
