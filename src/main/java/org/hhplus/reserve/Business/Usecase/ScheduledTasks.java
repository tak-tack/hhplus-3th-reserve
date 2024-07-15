package org.hhplus.reserve.Business.Usecase;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final QueueRepository queueRepository;

    @Scheduled(fixedRate = 1000)
    public void controlQueue(){
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        Integer countQueue = queueRepository.countWaitingQueue(QueueStatus.WAITING);
        if (countQueue >= 50)
        {
            queueRepository.updateQueueStatus(modifyDt,QueueStatus.WAITING);
        }

    }
}
