package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;

public interface ConcertRepository {

    ConcertOptionEntity findByConcert(Integer concertId);
}
