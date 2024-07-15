package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Integer> {
    //@Query("select concert_option_id,concert_id,concert_date from dba.concert_option where concert_id = :concertId")
    @Query("SELECT c.concertId  FROM ConcertEntity c")
    Optional<List<Integer>> findByConcertid();
//    @Query("SELECT concertId, concertName FROM ConcertEntity")
//    List<ConcertEntity> findAllConcert();

    @Query("SELECT c FROM ConcertEntity c "+
            "JOIN FETCH c.concertOptions co " +
            "JOIN FETCH co.concertSeats cs "+
            "WHERE c.concertId IN :concertIds")
    List<ConcertEntity> findConcertsWithSeats(@Param("concertIds") List<Integer> concertIds);


}