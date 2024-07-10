package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Presentation.DTO.Payment.BalanceResponseDTO;

public interface BalanceService {
    public BalanceResponseDTO BalanceSelect(Integer userId);
    public BalanceResponseDTO BalanceCharge(Integer userId, Integer amount);
}
