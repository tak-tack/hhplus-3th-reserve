package org.hhplus.reserve.Business.Domain.Concert.model;

import lombok.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertOptionDomain {

    private Integer concertOptionId;
    private String ConcertDate;
    private Set<ConcertSeatDomain> concertSeats;
}
