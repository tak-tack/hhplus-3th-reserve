package org.hhplus.reserve.Business.Service.UnitTest;

import org.hhplus.reserve.Business.Domain.Reservation.model.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.DB.Reservation.ReservationRepository;
import org.hhplus.reserve.Business.Domain.Reservation.ReservationServiceImpl;
import org.hhplus.reserve.Infrastructure.Entity.ReservationEntity;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceUnitTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        // 초기화 작업
    }

    @Test
    @DisplayName("콘서트 임시 예약")
    void testTemporaryReserve() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setConcertOptionId(1);
        reservationRequestDTO.setSeatId(1);
        reservationRequestDTO.setUserId(1);

        String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        ReservationDomain reservationDomain = ReservationEntity.builder().
                concertOptionId(1).seatId(1).userId(1).build().toDomain();

        doNothing().when(reservationRepository).register(
                reservationRequestDTO.getConcertOptionId(),
                ReservationStatus.RSERVATION_WATING.name(),
                reservationRequestDTO.getSeatId(),
                createDt,
                reservationRequestDTO.getUserId()
        );
        when(reservationRepository.find(reservationRequestDTO.getUserId(),ReservationStatus.RSERVATION_WATING)).thenReturn(reservationDomain);

        ReservationResponseDTO result = reservationService.temporaryReserve(reservationRequestDTO);

        assertNotNull(result);
        verify(reservationRepository).register(
                reservationRequestDTO.getConcertOptionId(),
                ReservationStatus.RSERVATION_WATING.name(),
                reservationRequestDTO.getSeatId(),
                createDt,
                reservationRequestDTO.getUserId()
        );
        verify(reservationRepository).find(reservationRequestDTO.getUserId(),ReservationStatus.RSERVATION_WATING);
    }

    @Test
    @DisplayName("콘서트 예약 완료 후 상태 변경")
    void testReserve() {
        String reservationStatus = "RESERVED";
        Integer reservationId = 1;
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        doNothing().when(reservationRepository).update(reservationStatus, modifyDt, reservationId);

        reservationService.reserve(reservationId);

        verify(reservationRepository).update(reservationStatus, modifyDt, reservationId);
    }
}