package org.hhplus.reserve.Interface.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.PaymentFacade;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    //private final UserFacade userFacade;
    private final PaymentFacade paymentFacade;
    /*
     **잔액 충전 / 조회 API**
     */
    @GetMapping("/{userId}/balance/select")
    public List<PaymentResponseDTO> BalanceSelect(@PathVariable(name="userId") Integer userId){
        return paymentFacade.userPaymentSelect(userId);
    }

    @PostMapping("/{userId}/balance/charge")
    public List<PaymentResponseDTO> BalanceCharge(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentFacade.userPaymentCharge(paymentRequestDTO);
    }
}
