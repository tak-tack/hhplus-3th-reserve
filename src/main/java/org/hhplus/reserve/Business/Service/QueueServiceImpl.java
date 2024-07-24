package org.hhplus.reserve.Business.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhplus.reserve.Business.Domain.QueueDomain;
import org.hhplus.reserve.Business.Repository.QueueRepository;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Business.Usecase.CustomException;
import org.hhplus.reserve.Business.Usecase.ErrorCode;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    //private static final Logger log = LoggerFactory.getLogger(QueueServiceImpl.class);
    // 서비스메인로직에 if 있으면 객체지향적 코드 쓰기힘들다. optional, 로직자체를 객체자체로 옮기면
    // @slf4j
    private final QueueRepository queueRepository;

    @Override
    @Transactional
    public List<QueueResponseDTO> applyQueue(Integer userId) {
        if (!queueRepository.exist(userId).isEmpty()) { // 중복체크
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
        if (queueRepository.findByUserId(userId, QueueStatus.PROCESSING)
                .stream().map(QueueDomain::toDTO).toList().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString());
        }
        return queueRepository.findByUserId(userId, QueueStatus.PROCESSING)
                .stream().map(QueueDomain::toDTO).toList();
    }

    @Override
    @Transactional
    public void updateQueue() {
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        Integer countQueue = queueRepository.countWaitingQueue(QueueStatus.WAITING);
        log.info("updateQueue countQueue : " + countQueue);
        List<Integer> queueIds = queueRepository.findQueueIdsByStatus(QueueStatus.WAITING);
        queueRepository.updateQueueStatusByIds(modifyDt, QueueStatus.PROCESSING, queueIds);

    }
}
