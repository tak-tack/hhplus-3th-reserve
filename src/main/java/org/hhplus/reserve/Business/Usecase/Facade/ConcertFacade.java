package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.*;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertFacade {
    private final TokenRedisService tokenRedisService;
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    public List<ConcertResponseDTO> reservationAvailable(){
        // 예약 가능 콘서트의 날짜, 좌석 반환
        return concertService.ConcertList();
    }

    @Transactional
    public ReservationResponseDTO reservationConcert(ReservationRequestDTO reservationRequestDTO){
        // 임시 예약 등록 완료
        ReservationResponseDTO reservationResponseDTO =
                reservationService.temporaryReserve(reservationRequestDTO);
        // 좌석 선점
        concertService.concertSeatUpdateToGetting(reservationResponseDTO.converting());
        // 결재 API 진입 ReservationStatus 반환
                paymentService.reservationPayment(
                reservationRequestDTO.getUserId(),
                concertService.concertSeatPrice(reservationResponseDTO.converting()) // seatPrice
                );
        // 콘서트 좌석 예약 완료
        reservationService.reserve(reservationResponseDTO.getReservationId());
        // 좌석 선점
        concertService.concertSeatUpdateToReserved(reservationResponseDTO.converting());
        // 활성화 토큰 만료
        tokenRedisService.deactivateToken(reservationRequestDTO.getSeatId().toString());
        return reservationResponseDTO;
    }
}