package org.hhplus.reserve.Business.Domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConcertOptionDomain {

    private Integer concertOptionId;
    private Integer concertId;
    private LocalDateTime concertDate;




}
