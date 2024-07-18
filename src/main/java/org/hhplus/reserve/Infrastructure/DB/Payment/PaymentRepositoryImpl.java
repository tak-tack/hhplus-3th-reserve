package org.hhplus.reserve.Infrastructure.DB.Payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Infrastructure.Entity.PaymentEntity;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private static final Logger log = LoggerFactory.getLogger(PaymentRepositoryImpl.class);
    private final PaymentJpaRepository paymentJpaRepository;

    // userId의 paymentAmount 반환

    @Override
    @Transactional
    public Integer findUserAmountByUserId(Integer userId) {
        log.info("paymentRepo - findUserAmountByUserId - userId: " + userId);
        return paymentJpaRepository.findUserAmountByUserId(userId).orElseThrow(RuntimeException::new);
    }

    // userId의 데이터 조회
    @Override
    @Transactional
    public List<PaymentDomain> findUserByUserId(Integer userId) {
        log.info("PaymentRepository findUserByUserId - userid : " + userId);
        return paymentJpaRepository.findUserByUserId(userId).orElseThrow(RuntimeException::new).stream().map(PaymentEntity::toDomain).toList();
    }

    // payment updat
    @Override
    @Transactional
    public void update(Integer paymentAmount, Integer userId) {
        log.info("PaymentRepository update - userid : " + userId);
        paymentJpaRepository.update(paymentAmount, userId);
    }

    // test 를 위한 등록
    @Override
    @Transactional
    public void register(Integer userId, Integer paymentAmount) {
        log.info("PaymentRepository save - userid : " + userId);
        log.info("PaymentRepository save - userid : " + paymentAmount);
        paymentJpaRepository.Register(userId, paymentAmount);
    }

}
