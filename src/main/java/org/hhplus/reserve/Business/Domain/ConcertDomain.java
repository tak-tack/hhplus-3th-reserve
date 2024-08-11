package org.hhplus.reserve.Business.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDomain {
    private Integer concertId;
    private String concertName;
    private Set<ConcertOptionDomain> concertOptions;

    public Set<ConcertResponseDTO> toDTO()
    {
        return concertOptions.stream()
                .map(option -> {
                    ConcertResponseDTO concertResponseDTO = new ConcertResponseDTO();
                    BeanUtils.copyProperties(this,concertResponseDTO);
                    concertResponseDTO.setConcertDate(option.getConcertDate());
                    concertResponseDTO.setSeats(option.getConcertSeats().stream()
                            .map(seat -> new ConcertResponseDTO.SeatInfo(seat.getConcertSeatNum(), seat.getConcertSeatPrice(),seat.getConcertSeatStatus()))
                            .collect(Collectors.toList()));
                    return  concertResponseDTO;

    }).collect(Collectors.toSet());

    }
}
