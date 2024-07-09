package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConcertJpaRepository extends JpaRepository<ConcertOptionEntity, Integer> {
    //@Query("select concertOptionId,concertId,concertDate from Concert_Option where concertId = :concertId")
    //ConcertOptionEntity findByConcertid(@Param("concertId") Integer concertId);
}
