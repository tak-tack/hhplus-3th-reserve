package org.hhplus.reserve.ControllerTest.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Infrastructure.DB.Payment.PaymentRepository;
import org.hhplus.reserve.Infrastructure.DB.Token.TokenRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertOptionJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertSeatJpaRepository;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ConcertControllerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(ConcertControllerIntegrationTest.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertOptionJpaRepository concertOptionJpaRepository;
    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    private ScheduledFuture<?> scheduledFuture;

    @BeforeEach
    void setUp() {
    }


    @AfterEach
    public void tearDown() {
        // 스케줄러 중지
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            log.info("스케줄러중지");
        }
    }


    @Test
    @DisplayName("예약 가능 조회 API - 성공")
    void ReservationAvailableSUCESS() throws Exception{
        UUID userUuid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("UUID",userUuid.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("예약 가능 조회 API - 실패 - CASE : 비인가 고객") // 수정예정
    void ReservationAvailableFAIL1() throws Exception{
        UUID userUuid = UUID.randomUUID();
        //tokenRepository.save(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("UUID",userUuid.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 성공")
    void ReservationSUCESS() throws Exception{
        //Integer userId = 33;
        UUID userUuid = UUID.randomUUID();
        paymentRepository.register(userUuid,100000); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO =
                new ReservationRequestDTO(userUuid,"2024-07-16",1,59);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("UUID",userUuid.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("콘서트 예약 API - 실패 - CASE : 중복된 유저")
    void ReservationFAIL1() throws Exception{
        Integer userId = 59;
        paymentRepository.register(userId,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(userId,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("UUID",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("콘서트 예약 API - 실패 - CASE : 잔액부족")
    void ReservationFAIL2() throws Exception{
        Integer userId = 67;
        //tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(userId,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(67,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("UUID",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

}