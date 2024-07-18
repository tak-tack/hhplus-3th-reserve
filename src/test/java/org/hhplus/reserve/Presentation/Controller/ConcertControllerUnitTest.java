package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Service.Impl.TokenServiceImpl;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Business.Usecase.UserFacade;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenServiceImpl tokenService;
    @MockBean
    private UserFacade userFacade;
    @InjectMocks
    ConcertController concertController;

//    ConcertAvailableResponseDTO concertAvailableResponseDTO =
//            ConcertAvailableResponseDTO.builder()
//                    .concertOptionId(1)
//                    .build();
//    ConcertAvailableResponseDTO.AvailableSeatDTO.


    @Test
    @DisplayName("토큰 발행 호출 테스트")
    void authentication() throws Exception{
        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO(1);
        ObjectMapper objectMapper = new ObjectMapper();
         mockMvc.perform(post("/concert/authentication").content(
                objectMapper.writeValueAsString(tokenRequestDTO))
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("예약 가능 조회 API")
    void ReservationAvailableSUCESS() throws Exception{
        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO(1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/availabilityConcertList").content(
                objectMapper.writeValueAsString(tokenRequestDTO))
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
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1,1000);
        ObjectMapper objectMapper = new ObjectMapper();
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