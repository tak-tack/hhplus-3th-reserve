package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Service.PaymentService;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final PaymentRepository paymentRepository;
    // 좌석 결재
    public String ReservationPayment(Integer userId, Integer seatPrice){
        Integer userBalance = paymentRepository.findUserAmountByUserId(userId); // user의 잔액 조회
        if(userBalance >= seatPrice){
            paymentRepository.update(userBalance-seatPrice,userId);
            return ReservationStatus.RESERVATION_FINISHED.name();
        }else{
            log.error("결재 실패");
            throw new RuntimeException();
        }
    }
    // 유저 잔액 조회
    public List<PaymentResponseDTO> UserPaymentFind(Integer userId){
        return paymentRepository.findUserByUserId(userId).stream().map(PaymentDomain::toDTO).toList();
    }
    // 유저 잔액 충전
    public List<PaymentResponseDTO> UserPaymentCharge(PaymentRequestDTO paymentRequestDTO){
        log.info("PaymentService - UserPaymentCharge - paymentRequestDTO.getUserId() : "+ paymentRequestDTO.getUserId());
        Integer userBalance =
                paymentRepository.findUserAmountByUserId(paymentRequestDTO.getUserId());
        log.info("PaymentService - UserPaymentCharge - beforeUserBalance : "+ userBalance);
        paymentRepository.update(userBalance + paymentRequestDTO.getChargeAmount(),paymentRequestDTO.getUserId());
        return paymentRepository.findUserByUserId(paymentRequestDTO.getUserId()).stream().map(PaymentDomain::toDTO).toList();
    }
}
