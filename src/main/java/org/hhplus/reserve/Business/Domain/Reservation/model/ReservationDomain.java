package org.hhplus.reserve.Business.Domain.Reservation.model;

import lombok.*;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationResponseDTO;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDomain {
    private Integer reservationId;
    private Integer userId;
    private Integer concertOptionId;
    private Integer seatId;
    private ReservationStatus reservationStatus;

    public ReservationResponseDTO toDTO()
    {
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        BeanUtils.copyProperties(this, reservationResponseDTO);
        return reservationResponseDTO;
    }
}
