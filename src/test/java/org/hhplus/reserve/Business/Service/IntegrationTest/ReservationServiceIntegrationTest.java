package org.hhplus.reserve.Business.Service.IntegrationTest;


import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.ReservationRepository;
import org.hhplus.reserve.Business.Service.ReservationServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Rollback
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    void testTemporaryReserve() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setConcertOptionId(1);
        reservationRequestDTO.setSeatId(1);
        reservationRequestDTO.setUserId(1);

        //String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        reservationService.temporaryReserve(reservationRequestDTO);

        List<ReservationDomain> reservations = reservationRepository.find(reservationRequestDTO.getUserId());
        assertNotNull(reservations);
        //assertFalse(reservations.isEmpty());

        ReservationDomain reservation = reservations.get(0);
        assertEquals(reservationRequestDTO.getUserId(), reservation.getUserId());
        assertEquals(ReservationStatus.RSERVATION_WATING.name(), reservation.getReservationStatus());
        //assertEquals(createDt, reservation.get());
    }

    @Test
    void testReserve() {
        Integer userId = 1;
        List<Integer> reservationIds = List.of(1, 2, 3);
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        // 사전에 예약을 등록합니다.
        reservationRepository.register(1, ReservationStatus.RSERVATION_WATING.name(), 1, modifyDt, userId);

        reservationService.reserve(ReservationStatus.RESERVATION_FINISHED.name(), reservationIds);

        // 예약 상태가 업데이트 되었는지 확인합니다.
        List<ReservationDomain> reservations = reservationRepository.find(userId);
        assertNotNull(reservations);
        for (ReservationDomain reservation : reservations) {
            assertEquals(ReservationStatus.RESERVATION_FINISHED.name(), reservation.getReservationStatus());
            //assertEquals(modifyDt, reservation.getModifyDt());
        }
    }
}