package org.hhplus.reserve.Business.Service.UnitTest;

import org.hhplus.reserve.Business.Domain.ReservationDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Infrastructure.DB.Reservation.ReservationRepository;
import org.hhplus.reserve.Business.Service.ReservationServiceImpl;
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
import java.util.Collections;
import java.util.List;

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

        List<ReservationDomain> reservationDomains = Collections.singletonList(new ReservationDomain());

        doNothing().when(reservationRepository).register(
                reservationRequestDTO.getConcertOptionId(),
                ReservationStatus.RSERVATION_WATING.name(),
                reservationRequestDTO.getSeatId(),
                createDt,
                reservationRequestDTO.getUserId()
        );
        when(reservationRepository.find(reservationRequestDTO.getUserId(),ReservationStatus.RSERVATION_WATING)).thenReturn(reservationDomains);

        List<ReservationResponseDTO> result = reservationService.temporaryReserve(reservationRequestDTO);

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
        List<Integer> reservationIds = List.of(1, 2, 3);
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        doNothing().when(reservationRepository).update(reservationStatus, modifyDt, reservationIds);

        reservationService.reserve(reservationStatus, reservationIds);

        verify(reservationRepository).update(reservationStatus, modifyDt, reservationIds);
    }
}