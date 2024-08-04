package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Infrastructure.DB.Payment.PaymentRepository;
import org.hhplus.reserve.Infrastructure.DB.Token.TokenRepository;
import org.hhplus.reserve.Presentation.DTO.Payment.PaymentRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class PaymentIntegrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("잔액 조회 API - 성공")
    void BalanceSelectSUCESS() throws Exception{
        Integer userId = 1;
        tokenRepository.save(userId); // 유저 토큰 생성
        paymentRepository.register(1,200000);
        mockMvc.perform(get("/payment/{userId}/balance/select",1)
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
        mockMvc.perform(get("/payment/{userId}/balance/select",3)
                        .header("userId",userId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("잔액 충전 API - 성공")
    void BalanceChargeSUCESS() throws Exception{
        Integer userId = 3;
        tokenRepository.save(userId); // 유저 토큰 생성
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(3,10000);
        paymentRepository.register(3,10000);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/payment/{userId}/balance/charge",paymentRequestDTO.getUserId())
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(paymentRequestDTO)) // for @RequestBody
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;

    }

    @Test
    @DisplayName("잔액 충전 API - 실패 - 찾을 수 없는 사용자")
    void BalanceChargeFAIL() throws Exception{
        Integer userId = 4;
        tokenRepository.save(5); // 유저 토큰 생성
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(4,10000);
        paymentRepository.register(4,10000);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/payment/{userId}/balance/charge",paymentRequestDTO.getUserId())
                        .header("userId",userId.toString())
                        .content(objectMapper.writeValueAsString(paymentRequestDTO)) // for @RequestBody
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;

    }
}
