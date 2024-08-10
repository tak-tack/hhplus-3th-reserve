package org.hhplus.reserve.Business.Usecase.Event;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.TokenRedisService;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TokenEventListener {
    private final TokenRedisService tokenRedisService;

    // 비동기로 이벤트 발행주체의 트랜잭션이 커밋된 후에 수행한다
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void TokenSuccessHandler(ReservationRequestDTO event){
        tokenRedisService.deactivateToken(event.getSeatId().toString());
    }
}
