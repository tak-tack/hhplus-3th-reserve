package org.hhplus.reserve.Business.Domain.Payment.Message;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMessage {
    private Integer userId;
    private Integer paymentAmount;

}
