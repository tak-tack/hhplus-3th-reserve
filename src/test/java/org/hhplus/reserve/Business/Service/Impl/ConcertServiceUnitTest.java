package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.ConcertDomain;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Concert.ConcertRepositoryImpl;
import org.hhplus.reserve.Infrastructure.Entity.ConcertEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

import static org.junit.jupiter.api.Assertions.*;

class ConcertServiceUnitTest {
    private static final Logger log = LoggerFactory.getLogger(ConcertServiceUnitTest.class);

    @Mock
    private ConcertEntity concertEntity;

    @Mock
    private ConcertJpaRepository concertJpaRepository;

    @Mock
    private ConcertRepositoryImpl concertRepository;

    @InjectMocks
    private ConcertServiceImpl concertService;

    private List<ConcertEntity> concertEntities;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
        concertEntities = Arrays.asList(
                //new ConcertEntity(1,"아이유콘서트"),
                //new ConcertEntity(2,"뉴진스콘서트")
        );

    }
//초기화하라는애가 하나있다
    @Test
    @DisplayName("공연 목록 조회")
    public void concertList(){
        // given
        List<ConcertDomain> concertDomains = concertEntities.stream()
                .map(ConcertEntity::toDomain)
                .collect(Collectors.toList());
        System.out.println("테스트코드 리스트 사이즈 : " + concertDomains.size());
        //given
        //when(concertRepository.findAll()).thenReturn(concertDomains);
        //when
        //List<ConcertDomain> concertList = concertService.ConcertList();
        //then
        //assertEquals(2,concertList.size());
    }
}