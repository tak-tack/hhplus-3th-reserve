package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Business.Domain.ReservationDomain;

public interface ReservationService {
    ReservationDomain reserve(Integer userId, Integer concertOptionId, Integer seatId);
}
