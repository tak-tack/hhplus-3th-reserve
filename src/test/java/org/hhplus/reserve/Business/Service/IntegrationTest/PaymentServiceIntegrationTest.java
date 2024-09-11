package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Infrastructure.DB.Payment.PaymentRepository;
import org.hhplus.reserve.Business.Domain.Payment.PaymentServiceImpl;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("예약 결제 Service - 성공")
    void ReservationPaymentSUCCESS() {
        UUID userUuid = UUID.randomUUID();
        Integer seatPrice = 100000;
        Integer userBalance = 200000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userUuid, userBalance);
        paymentService.reservationPayment(userUuid, seatPrice);
        assertEquals(userBalance - seatPrice, paymentRepository.findUserAmountByUserId(userUuid));
    }

    @Test
    @DisplayName("예약 좌석 결제 Service - 실패 - 잔액부족")
    void ReservationPaymentFAIL() {
        UUID userUuid = UUID.randomUUID();
        Integer seatPrice = 100000;
        Integer userBalance = 5000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userUuid, userBalance);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentService.reservationPayment(userUuid, seatPrice));

        assertNotNull(exception);
        assertEquals(userBalance, paymentRepository.findUserAmountByUserId(userUuid));
    }

    @Test
    @DisplayName("유저 잔액 조회 Service - 성공")
    void UserPaymentFindSUCESS() {
        UUID userUuid = UUID.randomUUID();

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userUuid, 10000);
        List<PaymentResponseDTO> result = paymentService.userPaymentFind(userUuid);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("유저 잔액 충전 Service - 성공")
    void UserPaymentChargeSUCESS() {
        UUID userUuid = UUID.randomUUID();
        Integer chargeAmount = 10000;
        Integer userBalance = 50000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userUuid, userBalance);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setUserUuid(userUuid);
        paymentRequestDTO.setChargeAmount(chargeAmount);

        List<PaymentResponseDTO> result = paymentService.userPaymentCharge(paymentRequestDTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userBalance + chargeAmount, paymentRepository.findUserAmountByUserId(userUuid));
    }
}