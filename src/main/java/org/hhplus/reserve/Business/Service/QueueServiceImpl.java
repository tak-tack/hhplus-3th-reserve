package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private static final Logger log = LoggerFactory.getLogger(QueueServiceImpl.class);
    private final QueueRepository queueRepository;

    @Override
    @Transactional
    public List<QueueResponseDTO> applyQueue(Integer userId) {
        if (!queueRepository.exist(userId).isEmpty()) {
            throw new CustomException(ErrorCode.USER_DUPLICATED, userId.toString());
        } else {
            String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
            queueRepository.saveByUserId(userId, createDt, QueueStatus.WAITING.name());
            return queueRepository.exist(userId).stream().map(QueueDomain::toDTO).toList();
        }
    }

    @Override
    @Transactional
    public List<QueueResponseDTO> checkQueue(Integer userId) {
        if (queueRepository.findByUserId(userId, QueueStatus.PROCESSING).stream().map(QueueDomain::toDTO).toList().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString());
        }
        return queueRepository.findByUserId(userId, QueueStatus.PROCESSING).stream().map(QueueDomain::toDTO).toList();
    }

    @Override
    @Transactional
    public void updateQueue() {
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        Integer countQueue = queueRepository.countWaitingQueue(QueueStatus.WAITING);
        log.info("updateQueue countQueue : " + countQueue);
        List<Integer> queueIds = queueRepository.findQueueIdsByStatus(QueueStatus.WAITING);
        log.info("updateQueue queueIds : " + queueIds.size());
        queueRepository.updateQueueStatusByIds(modifyDt, QueueStatus.PROCESSING, queueIds);

    }
}
