//package org.hhplus.reserve.Infrastructure.DB.Queue;
//
//import org.hhplus.reserve.Business.Domain.QueueDomain;
//import org.hhplus.reserve.Business.Enum.QueueStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//
//import java.time.Instant;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class QueueRedisRepositoryImplTest {
//
//    @Mock
//    private RedisTemplate<String, String> redisTemplate;
//    @Mock
//    private ZSetOperations<String, String> zSetOperations;
//    @Autowired
//    private QueueRedisRepositoryImpl queueRedisRepository;
//
//    String userId = "1";
//
//    @BeforeEach
//    public void setUp() {
//        Long score = Instant.now().toEpochMilli();
//        queueRedisRepository.register(userId,score);
//    }
//
//    @Test
//    @DisplayName("레디스 조회 테스트 - 성공")
//    void registerAndGetSUCESS() {
//
//        when(zSetOperations.range("Queue", 0, 0)).thenReturn(Set.of(userId));
//        Set<String> result =queueRedisRepository.getRange(0L,50L);
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertTrue(result.contains(userId));
//    }
//
//}