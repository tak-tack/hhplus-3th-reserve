package org.hhplus.reserve.Interface.DTO.Payment;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer userId;
    private Integer paymentAmount;
}