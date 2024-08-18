package org.hhplus.reserve.Interface.DTO.Reservation;
import lombok.*;

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
