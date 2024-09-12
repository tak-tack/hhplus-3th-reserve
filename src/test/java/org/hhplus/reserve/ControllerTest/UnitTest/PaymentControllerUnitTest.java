package org.hhplus.reserve.ControllerTest.UnitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Usecase.Facade.PaymentFacade;
import org.hhplus.reserve.Interface.Controller.PaymentController;
import org.hhplus.reserve.Interface.DTO.Payment.PaymentRequestDTO;
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
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthInterceptor authInterceptor;
    @MockBean
    private PaymentFacade paymentFacade;

    @Test
    @DisplayName("잔액 조회 API")
    void BalanceSelectSUCESS() throws Exception{
        UUID userUuid = UUID.randomUUID();
        Mockito.when(authInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        mockMvc.perform(get("/payment/{userUuid}/balance/select",userUuid)
                        .header("UUID",userUuid.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API")
    void BalanceChargeSUCESS() throws Exception{
        UUID userUuid = UUID.randomUUID();
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userUuid,1000);
        mockMvc.perform(post("/payment/{userUuid}/balance/charge",paymentRequestDTO.getUserUuid()).content(
                                objectMapper.writeValueAsString(paymentRequestDTO))
                        .header("UUID",userUuid.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
