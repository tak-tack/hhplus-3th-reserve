package org.hhplus.reserve.Business.Service.UnitTest;

import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;
import org.hhplus.reserve.Infrastructure.DB.Payment.PaymentRepository;
import org.hhplus.reserve.Business.Domain.Payment.PaymentServiceImpl;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        //Integer userId = 1;
        UUID userUuid = UUID.randomUUID();
        Integer seatPrice = 10000;
        Integer userBalance = 20000;

        when(paymentRepository.findUserAmountByUserId(userUuid)).thenReturn(userBalance);

        paymentService.reservationPayment(userUuid, seatPrice);

        verify(paymentRepository).update(userBalance - seatPrice, userUuid);
    }

    @Test
    @DisplayName("좌석 결재 테스트 실패 - 잔액 부족")
    void reservationPaymentFAIL1() {
        Integer seatPrice = 30000;
        Integer userBalance = 20000;
        UUID userUuid = UUID.randomUUID();

        when(paymentRepository.findUserAmountByUserId(userUuid)).thenReturn(userBalance);

        paymentService.reservationPayment(userUuid, seatPrice);

        verify(paymentRepository).update(userBalance - seatPrice, userUuid);
     }

    @Test
    @DisplayName("유저 잔액 조회 - 성공")
    void userPaymentFind() {
        //Integer userId = 1;
        UUID userUuid = UUID.randomUUID();
        List<PaymentDomain> paymentDomains = Collections.singletonList(new PaymentDomain());

        when(paymentRepository.findUserByUserId(userUuid)).thenReturn(paymentDomains);

        List<PaymentResponseDTO> result = paymentService.userPaymentFind(userUuid);

        assertNotNull(result);
        verify(paymentRepository).findUserByUserId(userUuid);
    }

    @Test
    @DisplayName("유저 잔액 충전 - 성공")
    void userPaymentCharge() {
        UUID userUuid = UUID.randomUUID();
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userUuid, 100);
        Integer userBalance = 50;

        when(paymentRepository.findUserAmountByUserId(paymentRequestDTO.getUserUuid())).thenReturn(userBalance);
        when(paymentRepository.findUserByUserId(paymentRequestDTO.getUserUuid())).thenReturn(Collections.singletonList(new PaymentDomain()));

        List<PaymentResponseDTO> result = paymentService.userPaymentCharge(paymentRequestDTO);

        verify(paymentRepository).update(userBalance + paymentRequestDTO.getChargeAmount(), paymentRequestDTO.getUserUuid());
        verify(paymentRepository).findUserByUserId(paymentRequestDTO.getUserUuid());

        assertNotNull(result);
    }
}