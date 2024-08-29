package org.hhplus.reserve.Business.Domain.Reservation;

import org.hhplus.reserve.Interface.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationResponseDTO;

public interface ReservationService {
    ReservationResponseDTO temporaryReserve(ReservationRequestDTO reservationRequestDTO);
    void reserve(Integer reservationId);

}
