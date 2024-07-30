package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Repository.PaymentRepository;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.QueueServiceImpl;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertOptionJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertSeatJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.concurrent.ScheduledFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private ScheduledTasks scheduledTasks;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;
    @Autowired
    private QueueServiceImpl queueServiceImpl;

    @BeforeEach
    void setUp() throws InterruptedException {
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

        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 50; i++) {
                // 토큰 저장
                tokenService.applyAuth(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <50; i++) {
                // 대기열 유저 저장
                final String create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
                queueServiceImpl.applyQueue(i);
            }
        });
        scheduledFuture = taskScheduler.scheduleAtFixedRate(scheduledTasks::controlQueue,500); // 스케줄러 실행
        /*
                // ScheduledExecutorService 생성, 1개의 스레드로 실행
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        scheduledFuture = executorService.scheduleAtFixedRate(scheduledTasks::controlQueue,0,1, TimeUnit.SECONDS); // 스케줄러 실행
         */
        // 쓰레드 시작
        thread1.start();
        thread2.start();

        // 두 쓰레드가 끝날 때까지 기다림
        thread1.join();
        thread2.join();
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
        Integer userId = 305;
        tokenRepository.save(userId); // 유저 토큰 생성
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(userId))
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
        Integer userId = 1007;
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
        Integer userId = 67;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(userId,100); // 유저 결재포인트 생성
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(67,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

}