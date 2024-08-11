package org.hhplus.reserve.Business.Usecase.Facade;


import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.QueueRedisService;
import org.hhplus.reserve.Business.Service.TokenRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProcessFacade {
    private static final Logger log = LoggerFactory.getLogger(ProcessFacade.class);
    @Autowired
    private QueueRedisService queueRedisService;
    @Autowired
    private TokenRedisService tokenRedisService;

    private static final long WAIT_INTERVAL = 50; // 대기 시간 (밀리초)
    private static final int MAX_ACTIVE_USERS = 10000; // 최대 활성 사용자 수

    @Transactional
    public boolean waitingQueue(String userId) throws InterruptedException {
        log.info("waitingQueue start");
        queueRedisService.saveQueue(Integer.parseInt(userId)); // 1차 대기열 진입

        while (true) {
            Long rank = queueRedisService.getQueueRank(userId);
            if (rank == null) {
                log.info("waitingQueue end");
                return true; // 1차 대기열 통과
            }else {
                TimeUnit.MILLISECONDS.sleep(WAIT_INTERVAL);
                log.info("queue is full processFirst loading");
            }
        }
    }
    @Transactional
    public boolean activeQueue(String userId) throws InterruptedException {
        while (true) {
            log.info("activeQueue start");
            if (tokenRedisService.activeTokenCount() < MAX_ACTIVE_USERS) {
                log.info("activeQueue active");
                tokenRedisService.activeToken(userId);
                return true; // 토큰 활성화 완료
            }
            TimeUnit.MILLISECONDS.sleep(WAIT_INTERVAL);
            log.info("activeToken is full processSecond loading");
        }
    }
}