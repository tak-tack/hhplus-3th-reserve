package org.hhplus.reserve.Business.Service;

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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    // 콘서트 임시 예약
    @Override
    @Transactional
    public List<ReservationResponseDTO> temporaryReserve(ReservationRequestDTO reservationRequestDTO){
        String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
            reservationRepository.register(reservationRequestDTO.getConcertOptionId(),
                    ReservationStatus.RSERVATION_WATING.name(),
                    reservationRequestDTO.getSeatId(),
                    createDt,
                    reservationRequestDTO.getUserId()
                    );
            return reservationRepository.find(reservationRequestDTO.getUserId()).stream().map(ReservationDomain::toDTO).toList();
    }
    //콘서트 예약 완료. 상태변경
    @Override
    @Transactional
    public void reserve(String reservationStatus, List<Integer> reservationIds){ // 반환타입 고민해보기
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        reservationRepository.update(reservationStatus,modifyDt,reservationIds);
    }

}
