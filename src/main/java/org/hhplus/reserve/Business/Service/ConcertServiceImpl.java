package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {
    private final ConcertRepository concertRepository;

    // 콘서트 조회
    @Override
    @Transactional
    public List<ConcertResponseDTO> ConcertList(){
        Integer concertId = concertRepository.findByConcertId();
        if(concertId == null)
        {
            throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
            }
            return concertRepository.findAllConcertWithSeats(concertId)
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
