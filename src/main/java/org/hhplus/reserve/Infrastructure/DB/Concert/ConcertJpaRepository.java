package org.hhplus.reserve.Infrastructure.DB.Concert;

import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Integer> {

    // 예약가능 콘서트 조회
    @Query("SELECT c.concertId  FROM ConcertEntity c")
    Optional<List<Integer>> findConcertId(Pageable pageable);

    // 예약가능 콘서트 좌석/날짜 조회
//    @Query("SELECT c,co,cs FROM ConcertEntity c " +
//            "JOIN FETCH ConcertOptionEntity co ON co.concertId = c.concertId " +
//            "JOIN FETCH ConcertSeatEntity cs ON cs.concertOptionId = co.concertOptionId " +
//            "WHERE c.concertId in (:concertId)")
    @Query("SELECT c FROM ConcertEntity c " +
            "JOIN FETCH c.concertOptions co " +
            "JOIN FETCH co.concertSeats cs " +
            "WHERE c.concertId IN :concertId")
    List<ConcertEntity> findConcertsWithSeats(@Param("concertId") List<Integer> concertId);


    // 콘서트 예약 시 seat 상태 update
    @Modifying
    @Query("UPDATE ConcertSeatEntity cs SET cs.concertSeatStatus = :concertSeatStatus, cs.modifyDt = :modifyDt" +
            " WHERE cs.concertSeatId = :concertSeatId AND cs.concertOption.concertOptionId = :concertOptionId AND cs.concertSeatStatus = :currentConcertSeatStatus")
    void updateConcertSeat(@Param("concertSeatStatus")ConcertSeatStatus concertSeatStatus,
                           @Param("modifyDt") String modifyDt,
                           @Param("concertSeatId")Integer concertSeatId,
                           @Param("concertOptionId") Integer concertOptionId,
                           @Param("currentConcertSeatStatus") ConcertSeatStatus currentConcertSeatStatus);

    // 결재 API 를 위한 예약 좌석 seatId로 seatPrcie 조회
    @Query("SELECT cs.concertSeatPrice FROM ConcertSeatEntity cs" +
            " WHERE cs.concertSeatId = :concertSeatId AND cs.concertOption.concertOptionId = :concertOptionId AND cs.concertSeatStatus = :currentConcertSeatStatus")
    Optional<Integer> findPriceBySeatId(@Param("concertSeatId") Integer concertSeatId,
                                        @Param("concertOptionId") Integer concertOptionId,
                                        @Param("currentConcertSeatStatus") ConcertSeatStatus currentConcertSeatStatus);

}