package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.*;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final TokenService tokenService;
    private final QueueService queueService;
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    private final ScheduledTasks scheduledTasks;

    public TokenResponseDTO AuthenticationApplication(TokenRequestDTO tokenRequestDTO){
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }
    public List<ConcertResponseDTO> ReservationAvailable(TokenRequestDTO tokenRequestDTO){
        // 대기열 진입
       queueService.applyQueue(tokenRequestDTO.getUserId());
        // 대기열 통과 1초당 50명씩 스케줄링으로 구현되어있긴함
        scheduledTasks.controlQueue();
        // 대기열 검증
        queueService.checkQueue(tokenRequestDTO.getUserId());
        // 예약 가능 콘서트의 날짜, 좌석 반환
        return concertService.ConcertList();


    }
    public List<ReservationResponseDTO> ReservationConcert(ReservationRequestDTO reservationRequestDTO){
        // 대기열 진입
        queueService.applyQueue(reservationRequestDTO.getUserId());
        // 대기열 통과 1초당 50명씩 스케줄링으로 구현되어있긴함
        //scheduledTasks.controlQueue();
        // 대기열 검증
        //queueService.checkQueue(reservationRequestDTO.getUserId());
        // 좌석 임시 예약 완료
        List<ReservationResponseDTO> reservationResponseDTO =
                reservationService.temporaryReserve(reservationRequestDTO);
        // 결재 API 진입 을 위한 seatId의 seatPrice 추출
        Integer seatPrice = concertService.ConcertSeatPrice(reservationRequestDTO.getSeatId());
        // 결재 API 진입 ReservationStatus 반환
        String reservationStatus =
                paymentService.ReservationPayment(reservationRequestDTO.getUserId(),seatPrice);
        // 콘서트 좌석 예약 완료
        reservationService.reserve(reservationStatus,
                reservationResponseDTO.stream().map(ReservationResponseDTO::getReservationId).toList());
        // 좌석 선점
        concertService.ConcertSeatUpdateToReserved(reservationRequestDTO.getSeatId());
        //토큰 만료
        tokenService.expireToken(reservationRequestDTO.getUserId()); // 토큰책임은 도메인이갖고있으니 만료는 비동기로 돈다던가 토큰검증요청등 토큰만료가
        return reservationResponseDTO;
    }

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
