package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Infrastructure.DB.Queue.QueueJpaRepository;
import org.hhplus.reserve.Infrastructure.DB.Queue.QueueRepositoryImpl;
import org.hhplus.reserve.Infrastructure.Entity.QueueEntity;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

import static org.junit.jupiter.api.Assertions.*;

class QueueServiceUnitTest {

    @Mock
    private QueueEntity queueEntity;

    @Mock
    private QueueJpaRepository queueJpaRepository;

    @Mock
    private QueueRepositoryImpl queueRepository;

    @InjectMocks
    private QueueServiceImpl queueService;

    private List<QueueEntity> queueEntities;

    private final String  create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

    UUID uuid = UUID.randomUUID();
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화

        for(int i =0; i<1000; i++) {
            queueEntities = Arrays.asList(
                    new QueueEntity(i, uuid, QueueStatus.WAITING, create_dt, null)
                    //, new QueueEntity(2,uuid, QueueStatus.WAITING,create_dt,null)
            );
        }

    }

    @Test
    @DisplayName("대기열 등록 service - 성공")
    void applyQueue() {
        //given
        List<QueueDomain> queueDomains = queueEntities.stream().map(QueueEntity::toDomain).toList();
        //when
       queueRepository.saveByUserUuid(
                queueEntities.get(0).getUserUUID()
                ,queueEntities.get(0).getQueueStatus()
        );
        System.out.println("uuid : "+  queueEntities.get(0).getUserUUID());
        List<QueueResponseDTO> queueList = queueService.applyQueue(uuid);
        System.out.println("테스트 : " + queueList);
        //assertEquals(1, queueList.size());


    }
}