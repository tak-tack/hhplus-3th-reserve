package org.hhplus.reserve.Infrastructure.DB.Reservation;
import jakarta.persistence.LockModeType;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Integer> {

    @Modifying
    @Query(value="INSERT into dba.reservation (reservation_id,concert_option_id" +
            ", reservation_status" +
            ", seat_id" +
            ", user_id" +
            ", create_dt" +
            ", modify_dt) values (default,:concertOptionId, :reservationStatus, :seatId, :userId, :createDt , :modifyDt)",nativeQuery = true)
    void register(@Param("concertOptionId") Integer concertOptionId,
                  @Param("reservationStatus") String reservationStatus,
                  @Param("seatId") Integer seatId,
                  @Param("userId") Integer userId,
                  @Param("createDt") String createDt,
                  @Param("modifyDt") String modifyDt);

    @Modifying
    @Query(value = "UPDATE dba.reservation r SET r.reservation_status = :reservationStatus, r.modify_dt = :modifyDt WHERE r.reservation_id  = :reservationId", nativeQuery = true)
    void update(@Param("reservationStatus") String reservationStatus,@Param("modifyDt") String modifyDt,@Param("reservationId") Integer reservationId);

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReservationEntity r WHERE r.userId = :userId AND r.reservationStatus = :reservationStatus")
    Optional<ReservationEntity> findByUserId(@Param("userId") Integer userId,@Param("reservationStatus")ReservationStatus reservationStatus);
}
