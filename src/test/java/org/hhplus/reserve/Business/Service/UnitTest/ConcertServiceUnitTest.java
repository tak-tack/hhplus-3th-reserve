package org.hhplus.reserve.Business.Service.UnitTest;

import jakarta.persistence.EntityNotFoundException;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Business.Domain.ConcertSeatDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito를 사용한 테스트 환경을 설정합니다.
class ConcertServiceUnitTest {
    private static final Logger log = LoggerFactory.getLogger(ConcertServiceUnitTest.class);

    @Mock
    private ConcertRepository concertRepository;


    @InjectMocks
    private ConcertServiceImpl concertService;

    @BeforeEach
    void setUp(){

    }
//초기화하라는애가 하나있다
    @Test
    @DisplayName("공연 목록 조회 성공")
    public void concertList(){
        // 콘서트 데이터 저장 start
        Integer concertId = 1;
        Set<ConcertSeatDomain> concertSeatDomain = new HashSet<>();
        concertSeatDomain.add(new ConcertSeatDomain(1,1,1000,ConcertSeatStatus.WAITING));
        Set<ConcertOptionDomain> concertOptions = new HashSet<>();
        concertOptions.add(new ConcertOptionDomain(1,"2024-02-03",concertSeatDomain));

        ConcertDomain concertDomain = new ConcertDomain();
        concertDomain.setConcertOptions(concertOptions);  // concertOptions 초기화

        List<ConcertDomain> concertDomains = Collections.singletonList(concertDomain);

        when(concertRepository.findByConcertId()).thenReturn(concertId);
        when(concertRepository.findAllConcertWithSeats(concertId)).thenReturn(concertDomains);

        List<ConcertResponseDTO> result = concertService.ConcertList();

        assertNotNull(result);
        verify(concertRepository).findByConcertId();
        verify(concertRepository).findAllConcertWithSeats(concertId);
    }
    @Test
    @DisplayName("공연 목록 조회 실패 - 미존재")
    void ConcertListEmpty() {
        Integer concertId = 1;
        when(concertRepository.findByConcertId()).thenReturn(concertId);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            concertService.ConcertList();
        });

        assertEquals("등록된 콘서트가 없습니다.", exception.getMessage());
        verify(concertRepository).findByConcertId();
        verify(concertRepository, never()).findAllConcertWithSeats(concertId);
    }

    @Test
    @DisplayName("콘서트 좌석 가격 조회 - 성공")
    void ConcertSeatPrice() {
        Integer concertSeatId = 1;
        Integer concertOptionId = 1;
        Integer seatPrice = 100;
        ConcertRequestDTO concertRequestDTO = new ConcertRequestDTO();
        concertRequestDTO.setSeatId(concertSeatId);
        concertRequestDTO.setConcertOptionId(concertOptionId);

        when(concertRepository.findSeatPriceByConcertSeatId(
                concertSeatId,
                concertOptionId,
                ConcertSeatStatus.WAITING
        )).thenReturn(seatPrice);

        Integer result = concertService.concertSeatPrice(concertRequestDTO);

        assertNotNull(result);
        assertEquals(seatPrice, result);
        verify(concertRepository).findSeatPriceByConcertSeatId(concertSeatId,concertOptionId,ConcertSeatStatus.WAITING);
     //   verify(log).info("concertservie - concertSeatId : " + concertSeatId);
    }

    @Test
    @DisplayName("예약된 콘서트 좌석 선점 -")
    void ConcertSeatUpdateToReserved() {
        Integer concertSeatId = 1;
        Integer concertOptionId = 1;
        ConcertRequestDTO concertRequestDTO = new ConcertRequestDTO();
        concertRequestDTO.setSeatId(concertSeatId);
        concertRequestDTO.setConcertOptionId(concertOptionId);
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));


        doNothing().when(concertRepository).updateSeat(
                ConcertSeatStatus.RESERVED,
                modifyDt,
                concertSeatId,
                concertOptionId,
                ConcertSeatStatus.GETTING);

        concertService.concertSeatUpdateToReserved(concertRequestDTO);

        verify(concertRepository).updateSeat(
                ConcertSeatStatus.RESERVED,
                modifyDt,
                concertSeatId,
                concertOptionId,
                ConcertSeatStatus.GETTING);
    }
}