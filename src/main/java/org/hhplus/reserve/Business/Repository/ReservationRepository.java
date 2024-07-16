package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;

import java.util.List;

public interface ReservationRepository {
    void save(Integer concertOptionId, ReservationStatus reservationStatus, Integer seatId, Integer userId);
    List<ReservationDomain> find(Integer userId);
}
