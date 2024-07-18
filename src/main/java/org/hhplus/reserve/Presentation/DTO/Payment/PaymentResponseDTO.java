package org.hhplus.reserve.Presentation.DTO.Payment;

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