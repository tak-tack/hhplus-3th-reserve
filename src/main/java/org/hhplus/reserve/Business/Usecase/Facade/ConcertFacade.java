package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.*;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertFacade {
    private final TokenService tokenService;
    private final QueueService queueService;
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    private final ScheduledTasks scheduledTasks;
    public List<ConcertResponseDTO> reservationAvailable(TokenRequestDTO tokenRequestDTO){
        // 대기열 진입
        queueService.applyQueue(tokenRequestDTO.getUserId());
        // 대기열 검증
        queueService.checkQueue(tokenRequestDTO.getUserId());
        // 예약 가능 콘서트의 날짜, 좌석 반환
        return concertService.ConcertList();


    }
    public List<ReservationResponseDTO> reservationConcert(ReservationRequestDTO reservationRequestDTO){
        // 대기열 진입
        queueService.applyQueue(reservationRequestDTO.getUserId());
        // 대기열 검증
        queueService.checkQueue(reservationRequestDTO.getUserId());
        // 임시 예약 등록 완료
        List<ReservationResponseDTO> reservationResponseDTO =
                reservationService.temporaryReserve(reservationRequestDTO);
        // 좌석 선점
        concertService.concertSeatUpdateToGetting(reservationRequestDTO.getSeatId(),reservationRequestDTO.getConcertOptionId());
        // 결재 API 진입 을 위한 seatId의 seatPrice 추출
        Integer seatPrice = concertService.concertSeatPrice(reservationRequestDTO.getSeatId(),reservationRequestDTO.getConcertOptionId());
        // 결재 API 진입 ReservationStatus 반환
        String reservationStatus =
                paymentService.reservationPayment(reservationRequestDTO.getUserId(),seatPrice);
        // 콘서트 좌석 예약 완료
        reservationService.reserve(reservationStatus,
                reservationResponseDTO.stream().map(ReservationResponseDTO::getReservationId).toList());
        // 좌석 선점
        concertService.concertSeatUpdateToReserved(reservationRequestDTO.getSeatId(),reservationRequestDTO.getConcertOptionId());
        //토큰 만료
        tokenService.expireToken(reservationRequestDTO.getUserId());
        return reservationResponseDTO;
    }
}
