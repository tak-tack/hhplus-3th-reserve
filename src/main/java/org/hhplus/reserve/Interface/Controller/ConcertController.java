package org.hhplus.reserve.Interface.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.ConcertFacade;
import org.hhplus.reserve.Interface.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {
    private static final Logger log = LoggerFactory.getLogger(ConcertController.class);
    private final ConcertFacade concertFacade;
    /*
    예약 가능 날짜 / 좌석 API**
    */
    @PostMapping("/availabilityConcertList")
    public List<ConcertResponseDTO> ReservationAvailable(){
        log.info("ReservationAvailable Contoroller");
        return concertFacade.reservationAvailable();
    }

    /*
    **좌석 예약 요청 API**
     */
    @PostMapping("/reservation")
    public ReservationResponseDTO ReservationApplication(
            @RequestBody ReservationRequestDTO reservationRequestDTO
    ){
        log.info("DTO ConcertOptionId : "+reservationRequestDTO.getConcertOptionId());
        log.info("DTO SeatId : "+reservationRequestDTO.getSeatId());
        return concertFacade.reservationConcert(reservationRequestDTO);
    }

}
