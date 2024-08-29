package org.hhplus.reserve.Business.Domain.Concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Interface.DTO.Concert.ConcertRequestDTO;
import org.hhplus.reserve.Interface.DTO.Concert.ConcertResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<ConcertResponseDTO> ConcertList(){
        log.info("Concert Service start");
        Pageable pageable = PageRequest.of(0,5);
        List<Integer> concertIds = concertRepository.findByConcertId(pageable);
        log.info("Concert Service concertId : "+concertIds.toString());
        if(concertIds.isEmpty())
        {
            throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
            }
            return concertRepository.findAllConcertWithSeats(concertIds)
                    .stream().flatMap(concertDomain ->
                            concertDomain.toDTO().stream()).collect(Collectors.toList());
    }

    // 임시 예약 시 콘서트 좌석 상태 변경 ( WATING -> GETTING )
    @Override
    public void concertSeatUpdateToGetting(ConcertRequestDTO concertRequestDTO){
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        concertRepository.updateSeat(
                ConcertSeatStatus.GETTING,
                modifyDt,
                concertRequestDTO.getSeatId(),
                concertRequestDTO.getConcertOptionId(),
                ConcertSeatStatus.WAITING
                 );
    }

    // 결재 API 를 위한 콘서트 좌석 가격 조회
    @Override
    public Integer concertSeatPrice(ConcertRequestDTO concertRequestDTO){
        log.info("consertservice concertOptionId : "+concertRequestDTO.getConcertOptionId());
        log.info("consertservice seatid : "+concertRequestDTO.getSeatId());
        return concertRepository.findSeatPriceByConcertSeatId(
                concertRequestDTO.getSeatId(),
                concertRequestDTO.getConcertOptionId(),
                ConcertSeatStatus.GETTING
        );
    }

    //  결재 성공 후 콘서트 좌석 상태 변경 ( GETTING -> RESERVED )
    @Override
    public void concertSeatUpdateToReserved(ConcertRequestDTO concertRequestDTO){
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        concertRepository.updateSeat(
                ConcertSeatStatus.RESERVED,
                modifyDt,
                concertRequestDTO.getSeatId(),
                concertRequestDTO.getConcertOptionId(),
                ConcertSeatStatus.GETTING);
    }

}
