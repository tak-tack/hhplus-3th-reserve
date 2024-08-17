package org.hhplus.reserve.Business.Model;

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
