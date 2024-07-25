package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.UserFacade;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final UserFacade userFacade;
    /*
     **잔액 충전 / 조회 API**
     */
    @GetMapping("/{userId}/balance/select")
    public List<PaymentResponseDTO> BalanceSelect(@PathVariable(name="userId") Integer userId){
        return userFacade.UserPaymentSelect(userId);
    }

    @PostMapping("/{userId}/balance/charge")
    public List<PaymentResponseDTO> BalanceCharge(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return userFacade.UserPaymentCharge(paymentRequestDTO);
    }
}
