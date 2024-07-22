package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.*;
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

    public TokenResponseDTO AuthenticationApplication(TokenRequestDTO tokenRequestDTO){
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }
    public List<ConcertResponseDTO> ReservationAvailable(TokenRequestDTO tokenRequestDTO){
        // 토큰 발급 확인
       //TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(tokenRequestDTO.getUserId());
        // 대기열 진입
       //queueService.applyQueue(tokenResponseDTO.getUserId());
        // 예약 가능 콘서트의 날짜, 좌석 반환
        return concertService.ConcertList();

    }
    public List<ReservationResponseDTO> ReservationConcert(ReservationRequestDTO reservationRequestDTO){
        // 토큰 발급 확인
        TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(reservationRequestDTO.getUserId());
        // 대기열 진입
        queueService.applyQueue(tokenResponseDTO.getUserId());
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
