package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.DB.Reservation.ReservationRepository;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    // 콘서트 임시 예약
    @Override
    @Transactional
    public List<ReservationResponseDTO> temporaryReserve(ReservationRequestDTO reservationRequestDTO){
        String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
            reservationRepository.register(reservationRequestDTO.getConcertOptionId(),
                    ReservationStatus.RSERVATION_WATING.name(),
                    reservationRequestDTO.getSeatId(),
                    createDt,
                    reservationRequestDTO.getUserId()
                    );
            return reservationRepository.find(reservationRequestDTO.getUserId(),ReservationStatus.RSERVATION_WATING).stream().map(ReservationDomain::toDTO).toList();
    }
    //콘서트 예약 완료. 상태변경
    @Override
    @Transactional
    public void reserve(String reservationStatus, List<Integer> reservationIds){ // 반환타입 고민해보기
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        reservationRepository.update(reservationStatus,modifyDt,reservationIds);
    }

}
