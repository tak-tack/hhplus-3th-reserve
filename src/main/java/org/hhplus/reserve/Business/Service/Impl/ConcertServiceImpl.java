package org.hhplus.reserve.Business.Service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {
    private final ConcertRepository concertRepository;

    @Override
    public List<ConcertResponseDTO> ConcertList(){
        List<Integer> concertList = concertRepository.findByConcertid();
        if(concertList.isEmpty())
        {
            throw new EntityNotFoundException("콘서트 조회 결과가 없습니다");
            }
            return concertRepository.findAllConcertWithSeats(concertList)
                    .stream().flatMap(concertDomain ->
                            concertDomain.toDTO().stream()).collect(Collectors.toList());
    }

    @Override
    public void ConcertSeatUpdateToReserved(Integer concertSeatId){
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        concertRepository.updateSeat(ConcertSeatStatus.RESERVED,modifyDt,concertSeatId);
    }
}
