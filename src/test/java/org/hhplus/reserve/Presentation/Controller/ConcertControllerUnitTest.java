package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Domain.User.TokenService;
import org.hhplus.reserve.Business.Usecase.Facade.UserFacade;
import org.hhplus.reserve.Interface.Controller.ConcertController;
import org.hhplus.reserve.Interface.DTO.Reservation.ReservationRequestDTO;
import org.hhplus.reserve.Interface.DTO.Token.TokenResponseDTO;
import org.hhplus.reserve.interceptor.AuthInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConcertController.class)
public class ConcertControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserFacade userFacade;
    @MockBean
    private AuthInterceptor authInterceptor;
    @MockBean
    private TokenService tokenService; // TokenService 모킹


    @Test
    @DisplayName("예약 가능 조회 API")
    void ReservationAvailableSUCESS() throws Exception{
          UUID uuid = UUID.randomUUID();
        Integer userId = 1005;
        Mockito.when(tokenService.checkAuth(Mockito.anyInt())).thenReturn(
                TokenResponseDTO.builder().userId(1).user_UUID(uuid).create_dt("2024-07-25").build());
        Mockito.when(authInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/concert/availabilityConcertList")
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("콘서트 예약 API")
    void ReservationSUCESS() throws Exception{
        UUID uuid = UUID.randomUUID();
        Mockito.when(tokenService.checkAuth(Mockito.anyInt())).thenReturn(
                TokenResponseDTO.builder().userId(1).user_UUID(uuid).create_dt("2024-07-25").build());
        Mockito.when(authInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1,"2024-07-16",1,1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/concert/reservation").content(
                                objectMapper.writeValueAsString(reservationRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
