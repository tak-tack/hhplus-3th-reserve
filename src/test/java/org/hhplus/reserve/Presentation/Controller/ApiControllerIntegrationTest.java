package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.QueueService;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertOptionJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertSeatJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertOptionJpaRepository concertOptionJpaRepository;
    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private QueueService queueService;

    @BeforeEach
    void setUp()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

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


        for(int i = 1; i<60; i++)
        {
            final String create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
            queueRepository.saveByUserId(i,create_dt, QueueStatus.WAITING.name());
        }

        for(int i =1; i<60; i++)
        {
            final String create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
            ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(i,"2024-07-23",i,i);
            //queueRepository.saveByUserId(i,create_dt,QueueStatus.WAITING.name());
            // 토큰 저장
            tokenService.applyAuth(i);
            // 토큰 발급 확인
            TokenResponseDTO tokenResponseDTO = tokenService.checkAuth(reservationRequestDTO.getUserId());
            // 대기열 진입
            //queueService.applyQueue(tokenResponseDTO.getUserId());

        }
    }

    @Test
    @DisplayName("예약 가능 조회 API - 성공")
    void ReservationAvailableSUCESS() throws Exception{
        Integer userId = 1;
        paymentRepository.register(1,200000);
        //tokenRepository.save(userId);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("예약 가능 조회 API - 실패 - CASE : 비인가 고객")
    void ReservationAvailableFAIL1() throws Exception{
        Integer userId = 1;
        tokenRepository.save(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 성공")
    void ReservationSUCESS() throws Exception{
        Integer userId = 76;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(userId,100000); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO =
                new ReservationRequestDTO(userId,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 실패 - CASE : 중복된 유저")
    void ReservationFAIL1() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(1,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("콘서트 예약 API - 실패 - CASE : 잔액부족")
    void ReservationFAIL2() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(1,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("잔액 조회 API - 성공")
    void BalanceSelectSUCESS() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(1,200000);
        mockMvc.perform(get("/concert/{userId}/balance/select",1)
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 조회 API - 실패 - 찾을수없는 사용자")
    void BalanceSelectFAIL1() throws Exception{
        Integer userId = 1;
        tokenRepository.save(2); // 유저 토큰 생성
        paymentRepository.register(1,200000);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/concert/{userId}/balance/select",3)
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API - 성공")
    void BalanceChargeSUCESS() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId); // 유저 토큰 생성
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1,10000);
        paymentRepository.register(1,10000); // 테스트를 위해 저장
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/{userId}/balance/charge",paymentRequestDTO.getUserId())
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;

    }
}