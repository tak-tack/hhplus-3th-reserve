package org.hhplus.reserve.Business.Service.IntegrationTest;

import jakarta.transaction.Transactional;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Service.PaymentServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

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
        Integer userId = 1;
        Integer seatPrice = 100000;
        Integer userBalance = 200000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, userBalance);

        String result = paymentService.ReservationPayment(userId, seatPrice);

        assertNotNull(result);
        assertEquals(ReservationStatus.RESERVATION_FINISHED.name(), result);
        assertEquals(userBalance - seatPrice, paymentRepository.findUserAmountByUserId(userId));
    }

    @Test
    @DisplayName("예약 좌석 결제 Service - 실패 - 잔액부족")
    void ReservationPaymentFAIL() {
        Integer userId = 1;
        Integer seatPrice = 100000;
        Integer userBalance = 5000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, userBalance);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.ReservationPayment(userId, seatPrice);
        });

        assertNotNull(exception);
        assertEquals(userBalance, paymentRepository.findUserAmountByUserId(userId));
    }

    @Test
    @DisplayName("유저 잔액 조회 Service - 성공")
    void UserPaymentFindSUCESS() {
        Integer userId = 1;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, 10000);
        List<PaymentResponseDTO> result = paymentService.UserPaymentFind(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("유저 잔액 충전 Service - 성공")
    void UserPaymentChargeSUCESS() {
        Integer userId = 1;
        Integer chargeAmount = 10000;
        Integer userBalance = 50000;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, userBalance);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setUserId(userId);
        paymentRequestDTO.setChargeAmount(chargeAmount);

        List<PaymentResponseDTO> result = paymentService.UserPaymentCharge(paymentRequestDTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userBalance + chargeAmount, paymentRepository.findUserAmountByUserId(userId));
    }
}