package org.hhplus.reserve.Interface.DTO.Reservation;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    //private Integer userId;
    private UUID userUuid;
    private String concertDt;
    private Integer concertOptionId;
    private Integer seatId;
}
// 인포뱅크