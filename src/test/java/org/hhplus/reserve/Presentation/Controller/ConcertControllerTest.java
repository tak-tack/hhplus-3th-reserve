package org.hhplus.reserve.Presentation.Controller;

import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ConcertControllerTest.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    @DisplayName("토큰 발행 호출 테스트")
    void authentication() throws Exception{
        Integer userId = 1;
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(1,userId);
        //ConcertController concertController = new ConcertController();
        //given
        //given()
        //when
        mockMvc.perform(get("/concert/authentication/{userId}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }
}