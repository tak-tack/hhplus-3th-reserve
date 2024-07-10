package org.hhplus.reserve.Presentation.DTO.Payment;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequestDTO {
    private Integer userId;
    private Integer amount;
}


