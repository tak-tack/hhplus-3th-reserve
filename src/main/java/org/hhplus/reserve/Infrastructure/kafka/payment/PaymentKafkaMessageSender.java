package org.hhplus.reserve.Infrastructure.kafka.payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.Payment.Message.PaymentMessage;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
// 결제 관련 이벤트 를 kafka 전송 역할
public class PaymentKafkaMessageSender {
     private final KafkaTemplate<String, String> kafkaTemplate;


     public void sendPaymentSuccessMessage(PaymentMessage paymentMessage){
         // key : paymentId, message_value : paymentMessage
         kafkaTemplate.send("payment_success_topic",
                 paymentMessage.getUserUuid().toString(),
                 paymentMessage.getPaymentAmount().toString());
     }
     public void sendPaymentFailureMessage(PaymentMessage paymentMessage){
         kafkaTemplate.send("payment_failure_topic",
                 paymentMessage.getUserUuid().toString(),
                 paymentMessage.getPaymentAmount().toString());
     }
}
