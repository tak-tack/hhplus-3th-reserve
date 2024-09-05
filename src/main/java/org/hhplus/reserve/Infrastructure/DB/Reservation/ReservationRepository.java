package org.hhplus.reserve.Infrastructure.DB.Reservation;

import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

import java.util.UUID;

public interface ReservationRepository {
    void register(Integer concertOptionId, String reservationStatus, Integer seatId, String createDt, UUID userUuid);
    void update(String reservationStatus, String modifyDt, Integer reservationId);
    ReservationDomain find(UUID userUuid, ReservationStatus reservationStatus);
}
