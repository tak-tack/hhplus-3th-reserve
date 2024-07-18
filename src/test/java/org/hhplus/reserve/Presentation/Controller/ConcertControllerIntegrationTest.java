package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertOptionJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertSeatJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertOptionJpaRepository concertOptionJpaRepository;
    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp()
    {
        ConcertEntity concert1 = ConcertEntity.builder()
                .concertName("대환장 카녜왜스트 내한 공연")
                .concertOptions(new HashSet<>())
                .build();

        ConcertOptionEntity option1 = ConcertOptionEntity.builder()
                .concert(concert1)
                .ConcertDate("2024-07-16")
                .concertSeats(new HashSet<>())
                .build();

        ConcertOptionEntity option2 = ConcertOptionEntity.builder()
                .concert(concert1)
                .ConcertDate("2024-07-17")
                .concertSeats(new HashSet<>())
                .build();

        concertJpaRepository.save(concert1); // 먼저 concert1을 저장하여 ID를 생성
        // option1을 저장하여 ID를 생성
        concertOptionJpaRepository.save(option1);


        ConcertSeatEntity seat1 = ConcertSeatEntity.builder()
                .concertOption(option1)
                .concertSeatNum(1)
                .concertSeatPrice(10000)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .build();

        ConcertSeatEntity seat2 = ConcertSeatEntity.builder()
                .concertOption(option1)
                .concertSeatNum(2)
                .concertSeatPrice(20000)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .build();
        concertSeatJpaRepository.save(seat1); // seat1을 먼저 저장하여 ID를 생성
        concertSeatJpaRepository.save(seat2); // 그 다음 seat2를 저장하여 ID를 생성

        // option2를 저장하여 ID를 생성
        concertOptionJpaRepository.save(option2);

        ConcertSeatEntity seat3 = ConcertSeatEntity.builder()
                .concertOption(option2)
                .concertSeatNum(3)
                .concertSeatPrice(40000000)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .build();
        concertSeatJpaRepository.save(seat3); // 마지막으로 seat3를 저장하여 ID를 생성

        // 추가된 모든 옵션과 좌석을 concert1에 추가
        concert1.getConcertOptions().add(option1);
        concert1.getConcertOptions().add(option2);

        option1.getConcertSeats().add(seat1);
        option1.getConcertSeats().add(seat2);
        option2.getConcertSeats().add(seat3);

        concertJpaRepository.save(concert1);

    }

    @Test
    @DisplayName("토큰 발급 요청 API - 성공")
    void authenticationSUCESS() throws Exception{
        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO(1);
        mockMvc.perform(post("/concert/authentication").content(
                                objectMapper.writeValueAsString(tokenRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("토큰 발급 요청 API - 실패 - userId 중복")
    void authenticationFAIL1() throws Exception{
        tokenService.applyAuth(1);
        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO(1);
        mockMvc.perform(post("/concert/authentication").content(
                                objectMapper.writeValueAsString(tokenRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("예약 가능 조회 API - 성공")
    void ReservationAvailableSUCESS() throws Exception{
        tokenRepository.save(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                .content(objectMapper.writeValueAsString(1))
                .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                                .andDo(print());
    }

    @Test
    @DisplayName("예약 가능 조회 API - 실패 - CASE : 비인가 고객")
    void ReservationAvailableFAIL1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("예약 가능 조회 API - 실패 - CASE : 콘서트 목록이 없을 경우")
    void ReservationAvailableFAIL2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 성공")
    void ReservationSUCESS() throws Exception{
        tokenRepository.save(1); // 유저 토큰 생성
        paymentRepository.register(1,100000); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 실패 - CASE : 잔액부족")
    void ReservationFAIL() throws Exception{
        tokenRepository.save(1); // 유저 토큰 생성
        paymentRepository.register(1,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("잔액 조회 API - 성공")
    void BalanceSelectSUCESS() throws Exception{
        paymentRepository.register(1,200000);
        mockMvc.perform(get("/concert/{userId}/balance/select",1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 조회 API - 실패 - 찾을수없는 사용자")
    void BalanceSelectFAIL1() throws Exception{
        paymentRepository.register(1,200000);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/concert/{userId}/balance/select",3)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API - 성공")
    void BalanceChargeSUCESS() throws Exception{
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1,10000);
        paymentRepository.register(1,10000); // 테스트를 위해 저장
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/{userId}/balance/charge",paymentRequestDTO.getUserId()).content(
                                objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;

    }
}