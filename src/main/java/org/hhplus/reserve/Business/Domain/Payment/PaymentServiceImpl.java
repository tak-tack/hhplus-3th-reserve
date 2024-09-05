package org.hhplus.reserve.Business.Domain.Payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.Evnet.PaymentEvent;
import org.hhplus.reserve.Business.Domain.Payment.Message.PaymentMessage;
import org.hhplus.reserve.Business.Enum.EventType;
import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;
import org.hhplus.reserve.Infrastructure.DB.Payment.PaymentRepository;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Infrastructure.Producer.PaymentProducer;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    //private final PaymentEvent paymentEvent;
    // 예약 좌석 결제
    @Override
    @Transactional
    public void reservationPayment(/*Integer userId*/ UUID userUuid, Integer seatPrice){
        Integer userBalance = paymentRepository.findUserAmountByUserId(userUuid); // user 의 잔액 조회
        if(userBalance >= seatPrice){
            Integer afterUserBalance =  userBalance-seatPrice;
            paymentRepository.update(afterUserBalance,userUuid);
            PaymentMessage paymentMessage =
            PaymentMessage.builder().userUuid(userUuid).paymentAmount(afterUserBalance).build();
            PaymentEvent paymentEvent = new PaymentEvent(this,paymentMessage, EventType.SUCCESS);
            paymentProducer.sendPaymentEvent(paymentEvent);
        }else{ // 잔액 부족
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }
    }
    // 유저 잔액 조회
    @Override
    @Transactional
    public List<PaymentResponseDTO> userPaymentFind(Integer userId){
        return paymentRepository.findUserByUserId(userId).stream().map(PaymentDomain::toDTO).toList();
    }
    // 유저 잔액 충전
    @Override
    @Transactional
    public List<PaymentResponseDTO> userPaymentCharge(PaymentRequestDTO paymentRequestDTO){
        Integer userBalance =
                paymentRepository.findUserAmountByUserId(paymentRequestDTO.getUserId());
        paymentRepository.update(userBalance + paymentRequestDTO.getChargeAmount(),paymentRequestDTO.getUserId());
        return paymentRepository.findUserByUserId(paymentRequestDTO.getUserId()).stream().map(PaymentDomain::toDTO).toList();
    }


}