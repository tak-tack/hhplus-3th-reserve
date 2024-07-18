package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;

import java.util.List;

public interface ConcertService {

    List<ConcertResponseDTO> ConcertList();
    void ConcertSeatUpdateToReserved(Integer concertSeatId);
    Integer ConcertSeatPrice(Integer concertSeatId);

}
