package org.hhplus.reserve.Business.Domain.Payment.Evnet;

import lombok.Getter;
import org.hhplus.reserve.Business.Domain.Payment.Message.PaymentMessage;
import org.hhplus.reserve.Business.Enum.EventType;
import org.springframework.context.ApplicationEvent;

@Getter
//결제 프로세스의 중요한 상태 변경을 캡슐화하여,
// 애플리케이션 내의 다른 컴포넌트들이 이 변경을 감지하고 적절한 작업을 수행
public class PaymentEvent extends ApplicationEvent {
    private final PaymentMessage paymentMessage;
    private final EventType eventType;
    public PaymentEvent(Object source, PaymentMessage paymentMessage, EventType eventType){
        super(source);
        this.paymentMessage = paymentMessage;
        this.eventType = eventType;
    }
}