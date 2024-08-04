package org.hhplus.reserve.Infrastructure.DB.Reservation;

import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

import java.util.List;

public interface ReservationRepository {
    void register(Integer concertOptionId, String reservationStatus, Integer seatId, String createDt, Integer userId);
    void update(String reservationStatus, String modifyDt, List<Integer> reservationIds);
    List<ReservationDomain> find(Integer userId,ReservationStatus reservationStatus);
}
