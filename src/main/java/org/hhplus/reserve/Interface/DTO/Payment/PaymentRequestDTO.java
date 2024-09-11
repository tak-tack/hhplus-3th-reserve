package org.hhplus.reserve.Interface.DTO.Payment;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 결재 DTO
public class PaymentRequestDTO {
    //private Integer userId;
    private UUID userUuid;
    private Integer chargeAmount;
}


