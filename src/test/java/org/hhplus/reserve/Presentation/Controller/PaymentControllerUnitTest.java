package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Domain.User.TokenService;
import org.hhplus.reserve.Business.Usecase.Facade.UserFacade;
import org.hhplus.reserve.Interface.Controller.PaymentController;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
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

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserFacade userFacade;
    @MockBean
    private TokenService tokenService; // TokenService 모킹
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthInterceptor authInterceptor;

    @Test
    @DisplayName("잔액 조회 API")
    void BalanceSelectSUCESS() throws Exception{
        UUID uuid = UUID.randomUUID();
        Mockito.when(tokenService.checkAuth(Mockito.anyInt())).thenReturn(
                TokenResponseDTO.builder().userId(1).user_UUID(uuid).create_dt("2024-07-25").build());
        Mockito.when(authInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        mockMvc.perform(get("/payment/{userId}/balance/select",1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API")
    void BalanceChargeSUCESS() throws Exception{
        //tkenService.applyAuth(1);
        UUID uuid = UUID.randomUUID();
        Mockito.when(tokenService.checkAuth(Mockito.anyInt())).thenReturn(
                TokenResponseDTO.builder().userId(1).user_UUID(uuid).create_dt("2024-07-25").build());
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1,1000);
        mockMvc.perform(post("/payment/{userId}/balance/charge",paymentRequestDTO.getUserId()).content(
                                objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
