package org.hhplus.reserve.Business.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.ConcertAvailable.ConcertAvailableResponseDTO;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDomain {
    private Integer concertId;
    private String concertName;

    public ConcertResponseDTO toDTO()
    {
        ConcertResponseDTO concertResponseDTO = new ConcertResponseDTO();
        BeanUtils.copyProperties(this, concertResponseDTO);
        return concertResponseDTO;
    }
}
