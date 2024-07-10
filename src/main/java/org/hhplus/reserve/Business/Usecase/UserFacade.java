package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Domain.ConcertOptionDomain;
import org.hhplus.reserve.Business.Service.*;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.ConcertAvailable.ConcertAvailableResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.BalanceRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Payment.BalanceResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final TokenService tokenService;
    private final QueueService queueService;
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final BalanceService balanceService;

    public TokenResponseDTO AuthenticationApplication(TokenRequestDTO tokenRequestDTO){
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }
    public List<ConcertAvailableResponseDTO> ReservationAvailable(TokenRequestDTO tokenRequestDTO){
       TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(tokenRequestDTO.getUserId()); // 토큰 발급 확인
       queueService.applyQueue(tokenResponseDTO.getUser_UUID()); // 대기열 진입
        List<ConcertResponseDTO> concertResponseDTOs =
                concertService.ConcertList().stream().map(ConcertDomain::toDTO).toList(); // 예약 가능 콘서트 조회
        return concertService.getConcertAvailabillity(concertResponseDTOs).
                stream().map(ConcertOptionDomain::toDTO).toList(); // 예약가능 날짜/좌석 조회
    }
    public ReservationResponseDTO ReservationConcert(ReservationRequestDTO reservationRequestDTO){
        TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(reservationRequestDTO.getUserId()); // 토큰 발급 확인
        queueService.applyQueue(tokenResponseDTO.getUser_UUID()); // 대기열 진입
        return reservationService.reserve(reservationRequestDTO.getUserId(), // 콘서트 예약
                                            reservationRequestDTO.getConcertOptionId(),
                                            reservationRequestDTO.getSeatId()).toDTO();
    }

    public BalanceResponseDTO Balnace(BalanceRequestDTO balanceRequestDTO){
        balanceService.BalanceSelect(balanceRequestDTO.getUserId()); // 잔액 조회

        return balanceService.BalanceCharge(balanceRequestDTO.getUserId()
                ,balanceRequestDTO.getAmount());  // 잔액 충전
    }




}
