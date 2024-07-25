package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;

import java.util.List;

public interface ConcertService {

    List<ConcertResponseDTO> ConcertList();
    void concertSeatUpdateToGetting(Integer concertSeatId, Integer concertOptionId);
    void ConcertSeatUpdateToReserved(Integer concertSeatId, Integer concertOptionId);
    Integer ConcertSeatPrice(Integer concertSeatId, Integer concertOptionId);

}
