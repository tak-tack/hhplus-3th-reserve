package org.hhplus.reserve.Infrastructure.DB.Reservation;

import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value="INSERT into dba.reservation (reservation_id,concert_option_id" +
            ", reservation_status" +
            ", seat_id" +
            ", user_id" +
            ", create_dt" +
            ", modify_dt) values (default,:concertOptionId, :reservationStatus, :seatId, :userId, null, :modifyDt)",nativeQuery = true)
    void register(@Param("concertOptionId") Integer concertOptionId,
                  @Param("reservationStatus") String reservationStatus,
                  @Param("seatId") Integer seatId,
                  @Param("userId") Integer userId,
                  @Param("modifyDt") String modifyDt);

    @Modifying
    @Transactional
    @Query(value = "UPDATE dba.reservation r SET r.reservation_status = :reservationStatus, r.modify_dt = :modifyDt WHERE r.reservation_id in (:reservationIds)", nativeQuery = true)
    void update(String reservationStatus, String modifyDt, List<Integer> reservationIds);

    @Transactional
    @Query("SELECT r FROM ReservationEntity r WHERE r.userId = :userId")
    List<ReservationEntity> findByUserId(Integer userId);
}
