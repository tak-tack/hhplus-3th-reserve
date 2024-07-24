package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Business.Enum.ReservationStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    // 예약 좌석 결제
    @Override
    @Transactional
    public String ReservationPayment(Integer userId, Integer seatPrice){
        Integer userBalance = paymentRepository.findUserAmountByUserId(userId); // user의 잔액 조회
        if(userBalance >= seatPrice){
            paymentRepository.update(userBalance-seatPrice,userId);
            return ReservationStatus.RESERVATION_FINISHED.name();
        }else{ // 잔액 부족
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }
    }
    // 유저 잔액 조회
    @Override
    @Transactional
    public List<PaymentResponseDTO> UserPaymentFind(Integer userId){
        return paymentRepository.findUserByUserId(userId).stream().map(PaymentDomain::toDTO).toList();
    }
    // 유저 잔액 충전
    @Override
    @Transactional
    public List<PaymentResponseDTO> UserPaymentCharge(PaymentRequestDTO paymentRequestDTO){
        Integer userBalance =
                paymentRepository.findUserAmountByUserId(paymentRequestDTO.getUserId());
        paymentRepository.update(userBalance + paymentRequestDTO.getChargeAmount(),paymentRequestDTO.getUserId());
        return paymentRepository.findUserByUserId(paymentRequestDTO.getUserId()).stream().map(PaymentDomain::toDTO).toList();
    }
}
