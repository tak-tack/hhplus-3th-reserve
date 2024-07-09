package org.hhplus.reserve.Infrastructure.DB.Concert;

import jakarta.persistence.LockModeType;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Integer> {
    //@Query("select concert_option_id,concert_id,concert_date from dba.concert_option where concert_id = :concertId")
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //@Query("select concert_name from concert where concert_id = :concertId")
    //ConcertOptionEntity findByConcertid(@Param("concertId") Integer concertId);
//    @Query("SELECT concertId, concertName FROM ConcertEntity")
//    List<ConcertEntity> findAllConcert();

    //@Query("insert")


}