package org.hhplus.reserve.Business.Domain;

import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationDomain {
    private Integer reservationId;
    private UUID user_Uuid;
    private Integer concertOptionId;
    private Integer seatId;
    private ConcertSeatStatus concertSeatStatus;
    private LocalDateTime createDt;

    public ReservationResponseDTO toDTO()
    {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        BeanUtils.copyProperties(this, reservationResponseDTO);
        return reservationResponseDTO;
    }
}
