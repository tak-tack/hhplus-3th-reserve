package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.QueueRedisService;
import org.hhplus.reserve.Business.Service.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

@RequiredArgsConstructor
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final QueueService queueService;
    private final QueueRedisService queueRedisService;

//      @Scheduled(fixedRate = 1000) // 1s
//    public void controlQueue(){
//        log.info("scheduledTask");
//        queueService.updateQueue(); // 큐 상태 변경 1초당 50명, Waiting > Processing
//    }

     @Scheduled(fixedRate = 500)
    public void passQueue(){
          log.info("passQueue");
        queueRedisService.passQueue();
    }
}


