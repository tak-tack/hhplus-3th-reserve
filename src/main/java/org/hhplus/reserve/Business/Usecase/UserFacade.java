package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
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
       TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(tokenRequestDTO.getUserId()); // 토큰 발급 확인
       queueService.applyQueue(tokenResponseDTO.getUser_UUID()); // 대기열 진입
        return concertService.ConcertList(); // 예약 가능 콘서트의 날짜, 좌석 반환

    }
    public List<ReservationResponseDTO> ReservationConcert(ReservationRequestDTO reservationRequestDTO){
        TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(reservationRequestDTO.getUserId()); // 토큰 발급 확인
        queueService.applyQueue(tokenResponseDTO.getUser_UUID()); // 대기열 진입
        List<ReservationResponseDTO> reservationResponseDTO = reservationService.temporaryReserve(reservationRequestDTO); // 좌석 임시 예약 완료
        Integer seatPrice = concertService.ConcertSeatPrice(reservationRequestDTO.getSeatId());
        String reservationStatus =
                paymentService.ReservationPayment(reservationRequestDTO.getUserId(),seatPrice);// 결재 API 진입 ReservationStatus 반환
        reservationService.reserve(reservationStatus,
                reservationResponseDTO.stream().map(ReservationResponseDTO::getReservationId).toList());// 콘서트 좌석 예약 완료
        concertService.ConcertSeatUpdateToReserved(reservationRequestDTO.getSeatId());// 좌석 선점
        //토큰 만료
        return reservationResponseDTO;
    }

    public List<PaymentResponseDTO> UserPaymentSelect(Integer userId)
    {
        return paymentService.UserPaymentFind(userId); // 잔액조회
    }

    public List<PaymentResponseDTO> UserPaymentCharge(PaymentRequestDTO paymentRequestDTO)
    {
        return paymentService.UserPaymentCharge(paymentRequestDTO); // 잔액 충전
    }

//    public BalanceResponseDTO Balnace(BalanceRequestDTO balanceRequestDTO){
//        balanceService.BalanceSelect(balanceRequestDTO.getUserId()); // 잔액 조회
//
//        return balanceService.BalanceCharge(balanceRequestDTO.getUserId()
//                ,balanceRequestDTO.getAmount());  // 잔액 충전
//    }




}
