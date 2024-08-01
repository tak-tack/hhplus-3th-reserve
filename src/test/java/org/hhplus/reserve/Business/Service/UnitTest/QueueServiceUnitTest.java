package org.hhplus.reserve.Business.Service.UnitTest;

import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Service.QueueServiceImpl;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito를 사용한 테스트 환경을 설정합니다.
class QueueServiceUnitTest {

    @Mock
    private QueueRepository queueRepository;

    @Mock
    private ScheduledTasks scheduledTasks;

    @InjectMocks
    private QueueServiceImpl queueService;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("대기열 등록 service - 성공")
    void applyQueue() {
        Integer userId = 1;
        String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        List<QueueDomain> queueDomains = Collections.singletonList(new QueueDomain());

            //doNothing().when(scheduledTasks).controlQueue();
        when(queueRepository.exist(userId)).thenReturn(queueDomains);

        List<QueueResponseDTO> result = queueService.applyQueue(userId);

        //verify(queueRepository).controlQueue();
        //verify(scheduledTasks).controlQueue();
        verify(queueRepository).exist(userId);

        assertNotNull(result);
    }

}