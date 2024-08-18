package org.hhplus.reserve.Interface.DTO.Concert;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertRequestDTO {
    private Integer concertOptionId;
    private Integer seatId;

}
