package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Service.QueueServiceImpl;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Rollback
class QueueServiceIntegrationTest {

    @Autowired
    private QueueServiceImpl queueService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    @DisplayName("대기열 진입")
    void testApplyQueue() {
        Integer userId = 1;
        //doNothing().when(scheduledTasks).controlQueue();
        for(int i=0; i<99; i++){
            queueService.applyQueue(i);
        }
        List<QueueDomain> queueDomains = queueRepository.exist(userId);
        assertNotNull(queueDomains);
        assertFalse(queueDomains.isEmpty());
    }

    @Test
    @DisplayName("대기열 확인")
    void checkQueue(){
        for(int i=0; i<99; i++){
            queueService.applyQueue(i);
        }
        for(int i=0; i<99; i++){
            queueService.checkQueue(i);
        }

    }

}