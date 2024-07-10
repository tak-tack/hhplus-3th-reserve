package org.hhplus.reserve.Presentation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hhplus.reserve.Business.Usecase.ConcertServiceFacade;
import org.hhplus.reserve.Business.Repository.TokenRepository;
import org.hhplus.reserve.Business.Service.TokenService;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
    private ConcertServiceFacade concertServiceFacade;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
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

}