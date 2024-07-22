package org.hhplus.reserve.Business.Service.IntegrationTest;

import jakarta.transaction.Transactional;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Service.PaymentServiceImpl;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        // 필요한 초기화 작업
    }

    @Test
    void testReservationPaymentSuccess() {
        Integer userId = 1;
        Integer seatPrice = 100;
        Integer userBalance = 200;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, userBalance);

        String result = paymentService.ReservationPayment(userId, seatPrice);

        assertNotNull(result);
        assertEquals(ReservationStatus.RESERVATION_FINISHED.name(), result);
        assertEquals(userBalance - seatPrice, paymentRepository.findUserAmountByUserId(userId));
    }

    @Test
    void testReservationPaymentFailure() {
        Integer userId = 1;
        Integer seatPrice = 100;
        Integer userBalance = 50;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, userBalance);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.ReservationPayment(userId, seatPrice);
        });

        assertNotNull(exception);
        assertEquals(userBalance, paymentRepository.findUserAmountByUserId(userId));
    }

    @Test
    void testUserPaymentFind() {
        Integer userId = 1;

        // 테스트 데이터를 DB에 저장
        paymentRepository.register(userId, 100);

        List<PaymentResponseDTO> result = paymentService.UserPaymentFind(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testUserPaymentCharge() {
        Integer userId = 1;
        Integer chargeAmount = 100;
        Integer userBalance = 50;

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