package org.hhplus.reserve.Business.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hhplus.reserve.Presentation.DTO.ConcertAvailable.ConcertAvailableResponseDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConcertOptionDomain {

    private Integer concertOptionId;
    private Integer concertId;
    private LocalDateTime concertDate;

    public ConcertAvailableResponseDTO toDTO(){
        ConcertAvailableResponseDTO concertAvailableResponseDTO = new ConcertAvailableResponseDTO();
        BeanUtils.copyProperties(this,concertAvailableResponseDTO);
        return concertAvailableResponseDTO;

    }


}
