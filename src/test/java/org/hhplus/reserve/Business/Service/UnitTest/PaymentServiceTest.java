package org.hhplus.reserve.Business.Service.UnitTest;

import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Service.PaymentServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mockito를 사용한 테스트 환경을 설정합니다.
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("좌석 결재 테스트 성공")
    void reservationPaymentSUCESS() {
        Integer userId = 1;
        Integer seatPrice = 10000;
        Integer userBalance = 20000;

        when(paymentRepository.findUserAmountByUserId(userId)).thenReturn(userBalance);

        String result = paymentService.reservationPayment(userId, seatPrice);

        verify(paymentRepository).update(userBalance - seatPrice, userId);
        assertEquals(ReservationStatus.RESERVATION_FINISHED.name(), result);
    }

    @Test
    @DisplayName("좌석 결재 테스트 실패 - 잔액 부족")
    void reservationPaymentFAIL1() {
        Integer userId = 1;
        Integer seatPrice = 30000;
        Integer userBalance = 20000;

        when(paymentRepository.findUserAmountByUserId(userId)).thenReturn(userBalance);

        String result = paymentService.reservationPayment(userId, seatPrice);

        verify(paymentRepository).update(userBalance - seatPrice, userId);
        assertEquals(ReservationStatus.RESERVATION_FINISHED.name(), result);
    }

    @Test
    @DisplayName("유저 잔액 조회 - 성공")
    void userPaymentFind() {
        Integer userId = 1;
        List<PaymentDomain> paymentDomains = Collections.singletonList(new PaymentDomain());

        when(paymentRepository.findUserByUserId(userId)).thenReturn(paymentDomains);

        List<PaymentResponseDTO> result = paymentService.userPaymentFind(userId);

        assertNotNull(result);
        verify(paymentRepository).findUserByUserId(userId);
    }

    @Test
    @DisplayName("유저 잔액 충전 - 성공")
    void userPaymentCharge() {
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1, 100);
        Integer userBalance = 50;

        when(paymentRepository.findUserAmountByUserId(paymentRequestDTO.getUserId())).thenReturn(userBalance);
        when(paymentRepository.findUserByUserId(paymentRequestDTO.getUserId())).thenReturn(Collections.singletonList(new PaymentDomain()));

        List<PaymentResponseDTO> result = paymentService.userPaymentCharge(paymentRequestDTO);

        verify(paymentRepository).update(userBalance + paymentRequestDTO.getChargeAmount(), paymentRequestDTO.getUserId());
        verify(paymentRepository).findUserByUserId(paymentRequestDTO.getUserId());

        assertNotNull(result);
    }
}