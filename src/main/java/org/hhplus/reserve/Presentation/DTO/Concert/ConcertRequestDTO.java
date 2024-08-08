package org.hhplus.reserve.Presentation.DTO.Concert;

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
