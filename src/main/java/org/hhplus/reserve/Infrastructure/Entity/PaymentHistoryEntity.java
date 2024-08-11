package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hhplus.reserve.Business.Enum.PaymentStatus;

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
    private Integer seatId;
    private Integer seatPrice;
    private Integer paymentAmount;
    private PaymentStatus paymentStatus;
    private String createDt;
}
