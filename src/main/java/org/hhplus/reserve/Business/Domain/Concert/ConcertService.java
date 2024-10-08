package org.hhplus.reserve.Business.Domain.Concert;

import org.hhplus.reserve.Interface.DTO.Concert.ConcertRequestDTO;
import org.hhplus.reserve.Interface.DTO.Concert.ConcertResponseDTO;

import java.util.List;

public interface ConcertService {

    List<ConcertResponseDTO> ConcertList();
    void concertSeatUpdateToGetting(ConcertRequestDTO concertRequestDTO);
    void concertSeatUpdateToReserved(ConcertRequestDTO concertRequestDTO);
    Integer concertSeatPrice(ConcertRequestDTO concertRequestDTO);

}
