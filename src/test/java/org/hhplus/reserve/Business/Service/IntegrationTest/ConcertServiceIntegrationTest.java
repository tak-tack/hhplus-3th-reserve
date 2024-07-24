package org.hhplus.reserve.Business.Service.IntegrationTest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Business.Domain.ConcertSeatDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertServiceImpl;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class ConcertServiceIntegrationTest {

    @Autowired
    private ConcertServiceImpl concertService;

    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;


    @Test
    void testConcertListSuccess() {
        // 테스트 데이터를 DB에 저장
        // 예: ConcertDomain 객체를 저장

        List<Integer> concertIds = List.of(1, 2, 3);
        Set<ConcertSeatDomain> concertSeatDomain = new HashSet<>();
        concertSeatDomain.add(new ConcertSeatDomain(1,1,1000, ConcertSeatStatus.WAITING));
        Set<ConcertOptionDomain> concertOptions = new HashSet<>();
        concertOptions.add(new ConcertOptionDomain(1,"2024-02-03",concertSeatDomain));
// concertRepository 에 save 구현예정
//        concertIds.forEach(id -> {
//            // DB에 테스트 데이터 삽입 (예: ConcertDomain 객체)
//            concertJpaRepository.save(new ConcertDomain(id, "2024-02-03",concertOptions));
//        });

        List<ConcertResponseDTO> result = concertService.ConcertList();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testConcertListEmpty() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            concertService.ConcertList();
        });

        assertEquals("콘서트 조회 결과가 없습니다", exception.getMessage());
    }

    @Test
    void testConcertSeatPrice() {
        Integer concertSeatId = 1;
        Integer seatPrice = 100;

        // 테스트 데이터를 DB에 저장
        // 예: concertRepository.saveSeatPrice(concertSeatId, seatPrice);

        Integer result = concertService.ConcertSeatPrice(concertSeatId);

        assertNotNull(result);
        assertEquals(seatPrice, result);
    }

    @Test
    void testConcertSeatUpdateToReserved() {
        Integer concertSeatId = 1;
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        // 테스트 데이터를 DB에 저장
        // 예: concertRepository.saveSeat(concertSeatId, /* 다른 필드 초기화 */);

        concertService.ConcertSeatUpdateToReserved(concertSeatId);

        // 상태가 업데이트 되었는지 확인
        // 예: ConcertDomain concertDomain = concertRepository.findById(concertSeatId).orElse(null);
        // assertNotNull(concertDomain);
        // assertEquals(ConcertSeatStatus.RESERVED, concertDomain.getStatus());
    }
}