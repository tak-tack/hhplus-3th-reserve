package org.hhplus.reserve.Business.Domain.Payment.model;

import lombok.*;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDomain {
    private Integer paymentId;
    private UUID userUuid;
    private Integer paymentAmount;

    public PaymentResponseDTO toDTO()
    {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        BeanUtils.copyProperties(this, paymentResponseDTO);
        return paymentResponseDTO;
    }
}