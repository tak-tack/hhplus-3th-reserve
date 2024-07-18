package org.hhplus.reserve.Presentation.DTO.Reservation;

import lombok.*;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

import java.util.UUID;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private Integer userId;
    private String concertDt;
    private Integer concertOptionId;
    private Integer seatId;
}
