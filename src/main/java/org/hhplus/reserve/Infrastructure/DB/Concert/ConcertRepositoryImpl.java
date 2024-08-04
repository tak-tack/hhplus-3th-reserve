package org.hhplus.reserve.Infrastructure.DB.Concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Integer> findByConcertid() {
        return concertJpaRepository.findByConcertid().orElse(null);
    }

    @Override
    public List<ConcertDomain> findAllConcertWithSeats(List<Integer> concertIds) {
        return concertJpaRepository.findConcertsWithSeats(concertIds).stream().map(ConcertEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Integer findSeatPriceByConcertSeatId(Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus) {
        return concertJpaRepository.findPriceBySeatId(concertSeatId,concertOptionId,currentConcertSeatStatus).orElseThrow(NullPointerException::new);
    }

    @Override
    public void updateSeat(ConcertSeatStatus concertSeatStatus, String modifyDt, Integer concertSeatId, Integer concertOptionId,ConcertSeatStatus currentConcertSeatStatus) {
        concertJpaRepository.updateConcertSeat(concertSeatStatus, modifyDt, concertSeatId,concertOptionId, currentConcertSeatStatus);
    }

}
