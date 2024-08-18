package org.hhplus.reserve.Business.Domain.User.Event;

import org.hhplus.reserve.Interface.DTO.Reservation.ReservationRequestDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TokenEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public TokenEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }
    public void success(ReservationRequestDTO event){
        applicationEventPublisher.publishEvent(event);
    }

}
