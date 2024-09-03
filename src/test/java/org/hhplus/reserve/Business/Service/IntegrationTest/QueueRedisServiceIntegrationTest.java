package org.hhplus.reserve.Business.Service.IntegrationTest;

import org.hhplus.reserve.Business.Domain.Queue.QueueRedisService;
import org.hhplus.reserve.Infrastructure.DB.Queue.QueueRedisRepository;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@SpringBootTest
class QueueRedisServiceIntegrationTest {
   private static final Logger log = LoggerFactory.getLogger(QueueRedisServiceIntegrationTest.class);
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private ScheduledTasks scheduledTasks;
    private ScheduledFuture<?> scheduledFuture;
    @Autowired
    private QueueRedisService queueRedisService;
    @Autowired
    private QueueRedisRepository queueRedisRepository;

    @BeforeEach
    public void setUp() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for(int i =0; i<5000; i++)
            {
                //queueRedisService.saveQueue(i);
            }
        });

        scheduledFuture = taskScheduler.scheduleAtFixedRate(scheduledTasks::passQueue,500);
        // 쓰레드 시작
        thread1.start();
        thread1.join();
    }


    @AfterEach
    public void tearDown() {
        // 스케줄러 중지
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            log.info("스케줄러중지");
        }
    }

    @Test
    @DisplayName("대기열 저장 - 성공")
    void saveQueue() {
        UUID userUuid = UUID.randomUUID();
        queueRedisService.saveQueue(userUuid.toString());
    }

    @Test
    @DisplayName("대기열 상위 50 - 성공")
    void getQueueTop50() {
        Set<String> sets = queueRedisService.getQueueTop50();
        log.info(sets.toString());
    }

    @Test
    @DisplayName("대기열 스코어 조회 - 성공")
    void getQueueScore() {
       long userIdScore = queueRedisService.getQueueScore("99").longValue();
        System.out.println(userIdScore);
    }

    @Test
    @DisplayName("대기열 스코어 삭제 - 성공")
    void removeQueue() {
        queueRedisService.removeQueue("99");

    }

    @Test
    @DisplayName("대기열 스코어 랭크 조회 - 성공")
    void rankQueue(){
        Long rank = queueRedisService.getQueueRank("99");
        System.out.println("rank : "+rank);

    }
    @Test
    @DisplayName("대기열 통과 - 30초대기")
    void passQueue30Waiting() throws InterruptedException {
        Thread.sleep(30000);
        log.info("잔여 대기열 갯수 : "+ queueRedisRepository.count());
        log.info("잔여 대기열 : "+ queueRedisRepository.getRange(0L,-1L));

    }
    @Test
    @DisplayName("대기열 통과 - 20초대기")
    void passQueue20Waiting() throws InterruptedException {
        Thread.sleep(20000);
        log.info("잔여 대기열 갯수 : "+ queueRedisRepository.count());
        log.info("잔여 대기열 : "+ queueRedisRepository.getRange(0L,-1L));

    }
    @Test
    @DisplayName("대기열 통과 - 10초대기")
    void passQueue10Waiting() throws InterruptedException {
        Thread.sleep(10000);
        log.info("잔여 대기열 갯수 : "+ queueRedisRepository.count());
        log.info("잔여 대기열 : "+ queueRedisRepository.getRange(0L,-1L));
    }
    @Test
    @DisplayName("대기열 통과 - 5초대기")
    void passQueue5Waiting() throws InterruptedException {
        Thread.sleep(5000);
        log.info("잔여 대기열 갯수 : "+ queueRedisRepository.count());
        log.info("잔여 대기열 : "+ queueRedisRepository.getRange(0L,-1L));
    }
}