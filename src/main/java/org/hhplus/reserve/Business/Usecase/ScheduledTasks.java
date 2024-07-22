package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final QueueRepository queueRepository;

    @Scheduled(fixedRate = 10)
    public void controlQueue(){
        log.info("ScheduledTasks - controlQueue");
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        Integer countQueue = queueRepository.countWaitingQueue(QueueStatus.WAITING);
        if (countQueue >= 50)
        {
            queueRepository.updateQueueStatus(modifyDt,QueueStatus.PROCESSING);
        }

    }
}
