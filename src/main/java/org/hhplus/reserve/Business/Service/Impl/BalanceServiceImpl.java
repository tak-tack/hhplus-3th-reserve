package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.BalanceDomain;
import org.hhplus.reserve.Business.Service.BalanceService;
import org.hhplus.reserve.Presentation.DTO.Payment.BalanceResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    public BalanceResponseDTO BalanceSelect(Integer userId){
        return null; // 잔액 조회 repo 반환 예정
    }
    public BalanceResponseDTO BalanceCharge(Integer userId, Integer amount){
        return null; // 잔액 충전 repo 반환예정
    }
}
