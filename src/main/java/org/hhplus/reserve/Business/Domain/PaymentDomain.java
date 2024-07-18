package org.hhplus.reserve.Business.Domain;

import lombok.*;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentResponseDTO;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDomain {
    private Integer paymentId;
    private Integer userId;
    private Integer paymentAmount;

    public PaymentResponseDTO toDTO()
    {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        BeanUtils.copyProperties(this, paymentResponseDTO);
        return paymentResponseDTO;
    }
}