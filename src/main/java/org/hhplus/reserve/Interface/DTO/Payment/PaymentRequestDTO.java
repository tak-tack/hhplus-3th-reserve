package org.hhplus.reserve.Interface.DTO.Payment;

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


