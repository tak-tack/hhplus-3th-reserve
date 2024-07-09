package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Business.Repository.ConcertRepository;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConcertServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertService concertService;
    @Autowired
    private ConcertRepository concertRepository;
    private List<ConcertEntity> concertEntities;
    @BeforeEach
    void setUp() {
//        concertEntities = Arrays.asList(
//                new ConcertEntity(1, "아이유콘서트"),
//                new ConcertEntity(2, "뉴진스콘서트")
//        );
    }
    @Test
    @DisplayName("공연 목록 조회 - 성공")
    void concertList() {
                ConcertEntity concertEntity = ConcertEntity.builder()
                .concertId(3)
                .concertName("박효신콘서트")
                .build();
        concertJpaRepository.save(concertEntity);
        List<ConcertDomain> concertList = concertService.ConcertList().stream().toList();
        assertEquals(1,concertList.size());
        assertEquals("박효신콘서트",concertList.get(0).getConcertName());
    }
}