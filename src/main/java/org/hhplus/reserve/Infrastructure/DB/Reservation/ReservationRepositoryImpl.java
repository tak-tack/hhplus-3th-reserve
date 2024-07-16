package org.hhplus.reserve.Infrastructure.DB.Reservation;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.ReservationRepository;
import org.hhplus.reserve.Infrastructure.Entity.ReservationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    public void save(Integer concertOptionId, ReservationStatus reservationStatus, Integer seatId, Integer userId){
        reservationJpaRepository.InsertReservation(concertOptionId,reservationStatus,seatId,userId,null);
    }

    public List<ReservationDomain> find(Integer userId){
        return reservationJpaRepository.findByUserId(userId).stream().map(ReservationEntity::toDomain).toList();
    }
}
