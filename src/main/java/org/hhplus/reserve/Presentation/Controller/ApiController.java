package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.UserFacade;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ApiController {
    private final UserFacade userFacade;
    /*
    예약 가능 날짜 / 좌석 API**
    */
    @PostMapping("/availabilityConcertList")
    public List<ConcertResponseDTO> ReservationAvailable(@RequestBody TokenRequestDTO tokenRequestDTO){
        return userFacade.ReservationAvailable(tokenRequestDTO);
    }

    /*
    **좌석 예약 요청 API**
     */
    @PostMapping("/reservation")
    public List<ReservationResponseDTO> ReservationApplication(
            @RequestBody ReservationRequestDTO reservationRequestDTO
    ){
        return userFacade.ReservationConcert(reservationRequestDTO);
    }
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
