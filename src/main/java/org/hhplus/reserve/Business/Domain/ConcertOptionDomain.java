package org.hhplus.reserve.Business.Domain;

import lombok.*;
import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
import org.hhplus.reserve.Presentation.DTO.ConcertAvailable.ConcertAvailableResponseDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
