package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.ConcertFacade;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertFacade concertFacade;
    /*
    예약 가능 날짜 / 좌석 API**
    */
    @PostMapping("/availabilityConcertList")
    public List<ConcertResponseDTO> ReservationAvailable(@RequestBody TokenRequestDTO tokenRequestDTO){
        return concertFacade.reservationAvailable(tokenRequestDTO);
    }

    /*
    **좌석 예약 요청 API**
     */
    @PostMapping("/reservation")
    public List<ReservationResponseDTO> ReservationApplication(
            @RequestBody ReservationRequestDTO reservationRequestDTO
    ){
        return concertFacade.reservationConcert(reservationRequestDTO);
    }

}
