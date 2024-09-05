package org.hhplus.reserve.Business.Domain.Payment.Message;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMessage {
    //private Integer userId;
    private UUID userUuid;
    private Integer paymentAmount;

}
