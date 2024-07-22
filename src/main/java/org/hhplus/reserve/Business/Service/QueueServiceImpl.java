package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final ScheduledTasks scheduledTasks;

    @Override
    @Transactional
    public List<QueueResponseDTO> applyQueue(Integer userId){
        if(queueRepository.exist(userId) == null)
        {
            throw new CustomException(ErrorCode.USER_DUPLICATED,userId.toString());
        }else {
            String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
            queueRepository.saveByUserId(userId, createDt, QueueStatus.WAITING.name());
            scheduledTasks.controlQueue();

            return queueRepository.exist(userId).stream().map(QueueDomain::toDTO).toList();
        }
    }
}
