package org.hhplus.reserve.Infrastructure.Spring.payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.Evnet.PaymentEvent;
import org.hhplus.reserve.Business.Domain.Payment.Message.PaymentMessage;
import org.hhplus.reserve.Business.Enum.EventType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentSpringEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishPaymentSuccessEvent(PaymentMessage paymentMessage, EventType eventType){
        PaymentEvent event = new PaymentEvent(this, paymentMessage, eventType);
        applicationEventPublisher.publishEvent(event);
    }
}
