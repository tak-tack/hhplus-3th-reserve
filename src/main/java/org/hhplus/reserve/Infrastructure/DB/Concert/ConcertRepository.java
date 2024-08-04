package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;

import java.util.List;

public interface ConcertRepository {

    Integer findByConcertId();
    List<ConcertDomain> findAllConcertWithSeats(Integer concertId);
    Integer findSeatPriceByConcertSeatId(Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus);
    void updateSeat(ConcertSeatStatus concertSeatStatus, String modifyDt, Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus);
}
