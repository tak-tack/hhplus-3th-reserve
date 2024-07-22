package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ApiController.class)
public class ApiControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenRepository tokenRepository;
    @InjectMocks
    ApiController apiController;

    @Test
    @DisplayName("예약 가능 조회 API")
    void ReservationAvailableSUCESS() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/availabilityConcertList")
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(1))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("콘서트 예약 API")
    void ReservationSUCESS() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 조회 API")
    void BalanceSelectSUCESS() throws Exception{
        mockMvc.perform(get("/concert/{userId}/balance/select",1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API")
    void BalanceChargeSUCESS() throws Exception{
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1,1000);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/{userId}/balance/charge",paymentRequestDTO.getUserId()).content(
                                objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
