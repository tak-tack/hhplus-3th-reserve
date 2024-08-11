package org.hhplus.reserve.Infrastructure.DB.Payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.PaymentDomain;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Infrastructure.Entity.PaymentEntity;
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
        return paymentJpaRepository.findUserAmountByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND,userId.toString()));
    }

    // userId의 데이터 조회
    @Override
    @Transactional
    public List<PaymentDomain> findUserByUserId(Integer userId) {
        return paymentJpaRepository.findUserByUserId(userId).filter(list -> !list.isEmpty())
                .orElseThrow(() -> new RuntimeException("No payments found for user ID: " + userId))
                .stream().map(PaymentEntity::toDomain).toList();
    }

    // payment update
    @Override
    @Transactional
    public void update(Integer paymentAmount, Integer userId) {
        paymentJpaRepository.update(paymentAmount, userId);
    }

    // test 를 위한 등록
    @Override
    @Transactional
    public void register(Integer userId, Integer paymentAmount) {
        paymentJpaRepository.Register(userId, paymentAmount);
    }

}
