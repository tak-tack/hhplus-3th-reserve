package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Business.Domain.Concert.model.ConcertDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConcertRepository {

    List<Integer> findByConcertId(Pageable pageable);
    List<ConcertDomain> findAllConcertWithSeats(List<Integer> concertId);
    Integer findSeatPriceByConcertSeatId(Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus);
    void updateSeat(ConcertSeatStatus concertSeatStatus, String modifyDt, Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus);
}
