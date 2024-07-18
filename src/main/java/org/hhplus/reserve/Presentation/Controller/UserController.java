package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.UserFacade;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class UserController {
    private final TokenService tokenService;
    private final UserFacade userFacade;
    /*
    토큰 인증 발급 API
     */
    @PostMapping("/authentication")
    public TokenResponseDTO authentication(@RequestBody TokenRequestDTO tokenRequestDTO){
        return userFacade.AuthenticationApplication(tokenRequestDTO);
    }

    /*
    **좌석 예약 요청 API**
    - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
    - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
    - 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.

     */


}