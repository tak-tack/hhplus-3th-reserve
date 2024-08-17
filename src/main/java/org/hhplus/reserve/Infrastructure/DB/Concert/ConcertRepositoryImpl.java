package org.hhplus.reserve.Infrastructure.DB.Concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Concert.model.ConcertDomain;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private static final Logger log = LoggerFactory.getLogger(ConcertRepositoryImpl.class);
    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Integer> findByConcertId() {
        return concertJpaRepository.findConcertId().orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    }

    @Override
    public List<ConcertDomain> findAllConcertWithSeats(List<Integer> concertIds) {
        log.info("ConcertRepository concertId : "+concertIds.toString());
        return concertJpaRepository.findConcertsWithSeats(concertIds).stream()
                .map(ConcertEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Integer findSeatPriceByConcertSeatId(
            Integer concertSeatId,
            Integer concertOptionId,
            ConcertSeatStatus currentConcertSeatStatus
    ) {
        return concertJpaRepository.findPriceBySeatId(
                concertSeatId,
                concertOptionId,
                currentConcertSeatStatus
        ).orElseThrow(NullPointerException::new);
    }

    @Override
    public void updateSeat(
            ConcertSeatStatus concertSeatStatus,
            String modifyDt,
            Integer concertSeatId,
            Integer concertOptionId,
            ConcertSeatStatus currentConcertSeatStatus
    ) {
        concertJpaRepository.updateConcertSeat(
                concertSeatStatus,
                modifyDt,
                concertSeatId,
                concertOptionId,
                currentConcertSeatStatus);
    }

}
