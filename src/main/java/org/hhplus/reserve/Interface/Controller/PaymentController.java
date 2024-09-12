package org.hhplus.reserve.Interface.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.PaymentFacade;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    //private final UserFacade userFacade;
    private final PaymentFacade paymentFacade;
    /*
     **잔액 충전 / 조회 API**
     */
    @GetMapping("/{userUuid}/balance/select")
    public List<PaymentResponseDTO> BalanceSelect(@PathVariable(name="userUuid") UUID userUuid){
        return paymentFacade.userPaymentSelect(userUuid);
    }

    @PostMapping("/{userId}/balance/charge")
    public List<PaymentResponseDTO> BalanceCharge(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentFacade.userPaymentCharge(paymentRequestDTO);
    }
}
