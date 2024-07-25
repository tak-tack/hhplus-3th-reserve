package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Integer> {

    // 예약가능 콘서트 조회
    @Query("SELECT c.concertId  FROM ConcertEntity c")
    Optional<List<Integer>> findByConcertid();

    // 예약가능 콘서트 좌석/날짜 조회
    @Query("SELECT c FROM ConcertEntity c "+
            "JOIN FETCH c.concertOptions co " +
            "JOIN FETCH co.concertSeats cs "+
            "WHERE c.concertId IN :concertIds")
    List<ConcertEntity> findConcertsWithSeats(@Param("concertIds") List<Integer> concertIds);

    // 콘서트 예약 시 seat 상태 update
    @Modifying
    @Query("UPDATE ConcertSeatEntity SET  concertSeatStatus = :concertSeatStatus, modifyDt =:modifyDt" +
            " WHERE concertSeatId =:concertSeatId AND concertOption.concertOptionId =:concertOptionId AND concertSeatStatus = :currentConcertSeatStatus")
    void updateConcertSeat(@Param("concertSeatStatus")ConcertSeatStatus concertSeatStatus,
                           @Param("modifyDt") String modifyDt,
                           @Param("concertSeatId")Integer concertSeatId,
                           @Param("concertOptionId") Integer concertOptionId,
                           @Param("currentConcertSeatStatus") ConcertSeatStatus currentConcertSeatStatus);

    // 결재API 를 위한 예약 좌석 seatId로 seatPrcie 조회
    @Query("SELECT cs.concertSeatPrice FROM ConcertSeatEntity cs" +
            " WHERE cs.concertSeatId = :concertSeatId AND cs.concertOption.concertOptionId =:concertOptionId AND cs.concertSeatStatus = :currentConcertSeatStatus")
    Optional<Integer> findPriceBySeatId(@Param("concertSeatId") Integer concertSeatId,
                                        @Param("concertOptionId") Integer concertOptionId,
                                        @Param("currentConcertSeatStatus") ConcertSeatStatus currentConcertSeatStatus);

}