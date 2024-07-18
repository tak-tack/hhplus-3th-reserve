package org.hhplus.reserve.Presentation.DTO.Payment;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 결재 DTO
public class PaymentRequestDTO {
    private Integer userId;
    private Integer chargeAmount;
}


