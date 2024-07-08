package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Application.ConcertServiceFacade;
import org.hhplus.reserve.Business.Service.Impl.TokenService;
import org.hhplus.reserve.Presentation.DTO.ConcertAvailableResponseDTO;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {
    private final TokenService tokenService;
    private final ConcertServiceFacade concertServiceFacade;
    /*

     */
    @PostMapping("/authentication")
    public TokenResponseDTO authentication(@RequestBody TokenRequestDTO tokenRequestDTO){
        return concertServiceFacade.AuthenticationApplication(tokenRequestDTO);
    }

    /*
    예약 가능 날짜 / 좌석 API**
    - 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
    - 예약 가능한 날짜 목록을 조회할 수 있습니다.
    - 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.
        > 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
        >
     */
    @PostMapping("/reservation")
    public ConcertAvailableResponseDTO ReservationApplication(
            @RequestBody ConcertAvailableResponseDTO concertResponseDTO
    ){
        //concertResponseDTO.setUserId(1);
        return ConcertAvailableResponseDTO.builder().build();
    }

    /*
    **좌석 예약 요청 API**
    - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
    - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
    - 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.

     */
    @GetMapping("/{concertId}/availability")
    public ConcertAvailableResponseDTO ReservationAvailable(@PathVariable Integer concertId){
        return ConcertAvailableResponseDTO.builder().build();
    }

    /*
    **잔액 충전 / 조회 API**

- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.
     */
    @GetMapping("/{userId}/balance/select")
    public ConcertAvailableResponseDTO BalanceSelectApplication(){
        return ConcertAvailableResponseDTO.builder().build();
    }

    @GetMapping("/{userId}/balance/charge")
    public ConcertAvailableResponseDTO BalanceChargeApplication(){
        return ConcertAvailableResponseDTO.builder().build();
    }

    /*
    **결제 API**

- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.
     */
    @GetMapping("/{userId}/payment")
    public ConcertAvailableResponseDTO PaymentApplication(){
        return ConcertAvailableResponseDTO.builder().build();
    }
}
