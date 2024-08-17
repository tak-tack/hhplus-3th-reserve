package org.hhplus.reserve.Business.Service.IntegrationTest;


import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.DB.Reservation.ReservationRepository;
import org.hhplus.reserve.Business.Domain.Reservation.ReservationServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    @DisplayName("임시예약서비스 - 성공")
    void testTemporaryReserve() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setConcertOptionId(1);
        reservationRequestDTO.setSeatId(1);
        reservationRequestDTO.setUserId(1);
        reservationService.temporaryReserve(reservationRequestDTO);

        ReservationDomain reservation = reservationRepository.find(reservationRequestDTO.getUserId(),ReservationStatus.RSERVATION_WATING);
        assertNotNull(reservation);

        assertEquals(reservationRequestDTO.getUserId(), reservation.getUserId());
        assertEquals(ReservationStatus.RSERVATION_WATING, reservation.getReservationStatus());
    }

    @Test
    @DisplayName("예약 상태 변경 - 성공")
    void testReserve() {
        Integer userId = 1;
        Integer reservationId = 1;

        reservationService.reserve(reservationId);

        // 예약 상태가 업데이트 되었는지 확인
        ReservationDomain reservation = reservationRepository.find(userId,ReservationStatus.RSERVATION_WATING);
        assertNotNull(reservation);
            assertEquals(ReservationStatus.RESERVATION_FINISHED, reservation.getReservationStatus());

    }
}