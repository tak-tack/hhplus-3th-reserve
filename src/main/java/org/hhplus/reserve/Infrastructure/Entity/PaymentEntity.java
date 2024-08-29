package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hhplus.reserve.Business.Domain.Payment.model.PaymentDomain;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
@Builder
@Entity
@Table(name="PAYMENT")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @Column(unique = true) // 중복 미허용
    private Integer userId;
    private Integer paymentAmount;

    public PaymentDomain toDomain(){
        PaymentDomain paymentDomain = new PaymentDomain();
        BeanUtils.copyProperties(this,paymentDomain);
        return paymentDomain;
    }
}
