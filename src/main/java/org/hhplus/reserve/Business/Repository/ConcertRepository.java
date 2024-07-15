package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;

import java.util.List;

public interface ConcertRepository {

    //ConcertOptionEntity findByConcert(Integer concertId);
    List<Integer> findByConcertid();
    List<ConcertDomain> findAllConcertWithSeats(List<Integer> concertIds);
}
