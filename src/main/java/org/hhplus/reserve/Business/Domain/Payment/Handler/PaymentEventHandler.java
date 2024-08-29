package org.hhplus.reserve.Business.Domain.Payment.Handler;

import org.hhplus.reserve.Business.Domain.Payment.Evnet.PaymentEvent;
import org.hhplus.reserve.Business.Enum.EventType;

public class PaymentEventHandler {
    public void handle(PaymentEvent paymentEvent){
        if(paymentEvent.getEventType() == EventType.SUCCESS){// 결제 성공
            handlePaymentSuccess(paymentEvent);
        }else{
            handlePaymentFailure(paymentEvent);
        }
    }
    private void handlePaymentSuccess(PaymentEvent paymentEvent){
        // 결제 성공 시 로직
        //TokenEventPublisher.success(reservationRequestDTO);
    }
    private void handlePaymentFailure(PaymentEvent paymentEvent){
        // 결제 실패 시 로직
    }
}
