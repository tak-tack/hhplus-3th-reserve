package org.hhplus.reserve.Interface.consumer;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.Evnet.PaymentEvent;
import org.hhplus.reserve.Business.Domain.Payment.Handler.PaymentEventHandler;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentEventHandler paymentEventHandler;

    @KafkaListener(topics = "payment_events", groupId = "payment_group")
    public void consumePaymentEvent(PaymentEvent paymentEvent){
        paymentEventHandler.handle(paymentEvent);
    }
}