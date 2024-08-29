package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hhplus.reserve.Business.Domain.Payment.model.PaymentHistoryDomain;
import org.hhplus.reserve.Business.Enum.PaymentStatus;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Builder
@Entity
@Table(name="PAYMENT_HISTORY")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentHistoryId;
    private Integer userId;
    private Integer paymentAmount;
    private PaymentStatus paymentStatus;
    private String modifyDt;
    private String createDt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist() {
        this.createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public PaymentHistoryDomain toDomain() {
        PaymentHistoryDomain paymentHistoryDomain = new PaymentHistoryDomain();
        BeanUtils.copyProperties(this, paymentHistoryDomain);
        return paymentHistoryDomain;
    }
}
