package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Service.QueueService;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Usecase.ScheduledTasks;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final ScheduledTasks scheduledTasks;

    @Override
    public List<QueueResponseDTO> applyQueue(UUID userUuid){
        queueRepository.saveByUserUuid(userUuid,QueueStatus.WAITING);
        scheduledTasks.controlQueue();

        return queueRepository.findByUuid(userUuid).stream().map(QueueDomain::toDTO).toList();
    }
}
