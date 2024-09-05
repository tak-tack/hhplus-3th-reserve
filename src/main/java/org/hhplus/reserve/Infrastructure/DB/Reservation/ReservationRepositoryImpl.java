package org.hhplus.reserve.Infrastructure.DB.Reservation;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    // 예약 등록
    public void register(Integer concertOptionId, String reservationStatus, Integer seatId, String createDt, UUID userUuid){
        reservationJpaRepository.register(concertOptionId,reservationStatus,seatId,userUuid,createDt,null);
    }
    // 예약 업데이트
    public void update(String reservationStatus, String modifyDt, Integer reservationId)
    {
        reservationJpaRepository.update(reservationStatus,modifyDt,reservationId);
    }
    // 예약 상태 조회
    // 예약 조회
    public ReservationDomain find(UUID userUuid, ReservationStatus reservationStatus){
        return reservationJpaRepository.findByUserId(userUuid,reservationStatus).orElseThrow(NullPointerException::new).toDomain();
    }
}
