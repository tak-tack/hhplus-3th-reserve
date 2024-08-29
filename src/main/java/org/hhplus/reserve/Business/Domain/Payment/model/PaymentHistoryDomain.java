package org.hhplus.reserve.Business.Domain.Payment.model;

import org.hhplus.reserve.Business.Enum.PaymentStatus;

public class PaymentHistoryDomain {
    private Integer userId;
    private PaymentStatus paymentStatus;
    private Integer paymentAmount;
    private String modifyDt;
}
