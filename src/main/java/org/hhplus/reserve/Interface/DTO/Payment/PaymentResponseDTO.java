package org.hhplus.reserve.Interface.DTO.Payment;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Integer paymentId;
    private UUID userUuid;
    private Integer paymentAmount;
}