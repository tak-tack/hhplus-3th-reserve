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
    private static final Logger log = LoggerFactory.getLogger(ConcertServiceImpl.class);
    private final ConcertRepository concertRepository;

    // 콘서트 조회
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

    // 결재 API 를 위한 콘서트 좌석 가격 조회
    @Override
    public Integer ConcertSeatPrice(Integer concertSeatId){
        log.info("concertservie - concertSeatId : "+ concertSeatId);
        return concertRepository.findSeatPriceByConcertSeatId(concertSeatId);
    }

    //  결재 성공 후 콘서트 좌석 상태 변경
    @Override
    public void ConcertSeatUpdateToReserved(Integer concertSeatId){
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        concertRepository.updateSeat(ConcertSeatStatus.RESERVED,modifyDt,concertSeatId);
    }

}
