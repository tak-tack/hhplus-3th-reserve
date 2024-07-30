package org.hhplus.reserve.Infrastructure.DB.Queue;

import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QueueRedisRepositoryImplTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ZSetOperations<String, String> zSetOperations;
    @InjectMocks
    private QueueRedisRepositoryImpl queueRedisRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    }

    @Test
    void registerSUCESS() {
        Integer userId = 1;
        QueueDomain queueDomain = QueueDomain.builder().userId(userId).queueId(1).queueStatus(QueueStatus.PROCESSING).createDt("2024-07-31 00:00:00").modifyDt("2024-07-31 00:10:00").build();
        queueRedisRepository.register(queueDomain);
        when(zSetOperations.range("Queue", 0, 0)).thenReturn(Set.of(userId.toString()));
        Set<String> result =queueRedisRepository.getQueue(0L,50L); //1722359044119 9999999999999L
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(userId.toString()));

        //assertTrue(result.contains("1"));
        //verify(zSetOperations).add("대기열",anyString(),eq(userId));
    }

    @Test
    void getQueue() {
    }
}