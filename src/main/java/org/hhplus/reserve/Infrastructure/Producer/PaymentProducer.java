package org.hhplus.reserve.Infrastructure.Producer;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.Evnet.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private static final String TOPIC = "payment_events";
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void sendPaymentEvent(PaymentEvent paymentEvent){
        kafkaTemplate.send(TOPIC,paymentEvent.getPaymentMessage().getUserId().toString(),paymentEvent);
    }

}