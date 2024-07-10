package org.hhplus.reserve.Business.Domain;

import org.hhplus.reserve.Presentation.DTO.Payment.BalanceResponseDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class BalanceDomain {
    private Integer balanceId;
    private Integer userId;
    private Integer amount;
    private LocalDateTime createDt;

    public BalanceResponseDTO toDTO(){
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO();
        BeanUtils.copyProperties(this, balanceResponseDTO);
        return balanceResponseDTO;
    }
}
