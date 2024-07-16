package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.ReservationRepository;
import org.hhplus.reserve.Business.Service.ReservationService;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    // 콘서트 임시 예약
    public List<ReservationResponseDTO> temporaryReserve(ReservationRequestDTO reservationRequestDTO){

            log.info("ReservationService - reserve userId: "+ reservationRequestDTO.getUserId());
            log.info("ReservationService - reserve concertOptionId: "+ reservationRequestDTO.getConcertOptionId());
            log.info("ReservationService - reserve seatId: "+ reservationRequestDTO.getSeatId());
            //String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
            reservationRepository.save(reservationRequestDTO.getConcertOptionId(),
                    ReservationStatus.RSERVATION_WATING,
                    reservationRequestDTO.getSeatId(),
                    reservationRequestDTO.getUserId()
                    );
            //log.info("ReservationService - return : "+reservationRepository.find(reservationRequestDTO.getUserId()).toDTO().getReservationId());
            return reservationRepository.find(reservationRequestDTO.getUserId()).stream().map(ReservationDomain::toDTO).toList();


    }

}
