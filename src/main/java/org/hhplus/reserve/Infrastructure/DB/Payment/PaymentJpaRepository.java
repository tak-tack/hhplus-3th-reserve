package org.hhplus.reserve.Infrastructure.DB.Payment;

import org.hhplus.reserve.Infrastructure.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity,Integer> {

    // 결재 API 를 위한 잔액만 조회
    @Query("SELECT p.paymentAmount FROM PaymentEntity p WHERE p.userId = :userId")
    Optional<Integer> findUserAmountByUserId(@Param("userId") Integer userId);

    // 유저 잔액 조회
    // Entity 객체로 반환
    @Query("SELECT p FROM PaymentEntity p WHERE p.userId = :userId")
    Optional<List<PaymentEntity>> findUserByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "UPDATE dba.payment p SET p.payment_amount = :paymentAmount WHERE p.user_id =:userId",nativeQuery = true)
    void update(@Param("paymentAmount") Integer paymentAmount, @Param("userId") Integer userId);

    @Modifying
    @Query(value="INSERT INTO dba.payment (payment_id,user_id,payment_amount) values(default,:userId,:paymentAmount) ",nativeQuery = true)
    void Register(@Param("userId")Integer userId, @Param("paymentAmount")Integer paymentAmount);
}
