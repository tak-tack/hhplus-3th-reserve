package org.hhplus.reserve.Infrastructure.DB.Reservation;

import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

public interface ReservationRepository {
    void register(Integer concertOptionId, String reservationStatus, Integer seatId, String createDt, Integer userId);
    void update(String reservationStatus, String modifyDt, Integer reservationId);
    ReservationDomain find(Integer userId,ReservationStatus reservationStatus);
}
