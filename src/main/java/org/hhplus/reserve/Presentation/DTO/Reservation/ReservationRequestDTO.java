package org.hhplus.reserve.Presentation.DTO.Reservation;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private Integer userId;
    private Integer concertOptionId;
    private Integer seatId;
}
