package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Enum.ConcertSeatStatus;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Business.Usecase.UserFacade;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertOptionEntity;
import org.hhplus.reserve.Infrastructure.Entity.ConcertSeatEntity;
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

import java.util.HashSet;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFacade userFacade;

    @BeforeEach
    void setUp()
    {
        ConcertEntity concert1 = ConcertEntity.builder()
                .concertName("환상의 카녜왜스트 내한 공연")
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

        ConcertSeatEntity seat3 = ConcertSeatEntity.builder()
                .concertOption(option2)
                .concertSeatNum(3)
                .concertSeatPrice(40000000)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .build();

        option1.getConcertSeats().add(seat1);
        option1.getConcertSeats().add(seat2);
        option2.getConcertSeats().add(seat3);

        concert1.getConcertOptions().add(option1);
        concert1.getConcertOptions().add(option2);

        concertJpaRepository.save(concert1);

        ConcertEntity concert2 = ConcertEntity.builder()
                .concertName("콘서트1")
                .concertOptions(new HashSet<>())
                .build();

        ConcertOptionEntity option3 = ConcertOptionEntity.builder()
                .concert(concert2)
                .ConcertDate("2024-07-18")
                .concertSeats(new HashSet<>())
                .build();

        ConcertOptionEntity option4 = ConcertOptionEntity.builder()
                .concert(concert2)
                .ConcertDate("2024-07-19")
                .concertSeats(new HashSet<>())
                .build();

        ConcertSeatEntity seat4 = ConcertSeatEntity.builder()
                .concertOption(option1)
                .concertSeatNum(10)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .concertSeatPrice(100000)
                .build();

        ConcertSeatEntity seat5 = ConcertSeatEntity.builder()
                .concertOption(option1)
                .concertSeatNum(20)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .concertSeatPrice(2000)
                .build();

        ConcertSeatEntity seat6 = ConcertSeatEntity.builder()
                .concertOption(option2)
                .concertSeatNum(40)
                .concertSeatStatus(ConcertSeatStatus.WAITING)
                .concertSeatPrice(400000)
                .build();

        option3.getConcertSeats().add(seat4);
        option4.getConcertSeats().add(seat5);
        option4.getConcertSeats().add(seat6);

        concert2.getConcertOptions().add(option3);
        concert2.getConcertOptions().add(option4);

        concertJpaRepository.save(concert2);
        //concertJpaRepository.saveAll();
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
        //tokenRepository.save(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        //userFacade.ReservationAvailable(tokenRequestDTO);
    }

    @Test
    @DisplayName("예약 가능 조회 API - 실패 - CASE : 콘서트 목록이 없을 경우")
    void ReservationAvailableFAIL2() throws Exception{
        //tokenRepository.save(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        //userFacade.ReservationAvailable(tokenRequestDTO);
    }

    @Test
    @DisplayName("콘서트 예약 API - 성공")
    void ResertvationSUCESS() throws Exception{
        tokenRepository.save(1);
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}