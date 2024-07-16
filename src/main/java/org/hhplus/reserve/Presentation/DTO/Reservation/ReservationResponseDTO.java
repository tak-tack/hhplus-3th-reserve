package org.hhplus.reserve.Presentation.DTO.Reservation;

import lombok.*;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private Integer reservationId;
    private Integer userId;
    private Integer concertOptionId;
    private Integer seatId;
    private ReservationStatus reservationStatus;
}
