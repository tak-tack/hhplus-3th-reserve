package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;

import java.util.List;

public interface ReservationService {
    List<ReservationResponseDTO> temporaryReserve(ReservationRequestDTO reservationRequestDTO);
}
