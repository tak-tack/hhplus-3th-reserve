package org.hhplus.reserve.Business.Domain;

import lombok.*;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertSeatDomain {
    private Integer concertSeatId;
    private Integer concertSeatNum; // 1~50
    private Integer concertSeatPrice;
    private ConcertSeatStatus concertSeatStatus;
}
