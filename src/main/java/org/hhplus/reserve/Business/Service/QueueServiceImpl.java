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

    // 대기열 (큐) 진입
    @Override
    @Transactional
    public List<QueueResponseDTO> applyQueue(Integer userId) {
        if (!queueRepository.exist(userId).isEmpty()) { // 중복체크
            throw new CustomException(ErrorCode.USER_DUPLICATED, userId.toString());
        } else {
            String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
            QueueDomain queueDomain = QueueDomain.builder().userId(userId).createDt(createDt).queueStatus(QueueStatus.WAITING).build();
            queueRepository.register(queueDomain);
            return queueRepository.exist(userId).stream().map(QueueDomain::toDTO).toList();
        }
    }

    // 대기열(큐) 확인.. 내 앞에 몇명 구현 예정
    @Override
    @Transactional
    public List<QueueResponseDTO> checkQueue(Integer userId) {
        try {
            Thread.sleep(1000); //1초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (queueRepository.findByUserId(userId, QueueStatus.PROCESSING)
                .stream().map(QueueDomain::toDTO).toList().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString());
        }
        return queueRepository.findByUserId(userId, QueueStatus.PROCESSING)
                .stream().map(QueueDomain::toDTO).toList();
    }


    // 대기열(큐) 상태 변경
    @Override
    @Transactional
    public void updateQueue() {
        String modifyDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
        Integer countQueue = queueRepository.countWaitingQueue(QueueStatus.WAITING);
        log.info("updateQueue countQueue : " + countQueue);
        List<Integer> queueIds = queueRepository.findQueueIdsByStatus(QueueStatus.WAITING);
        log.info("updateQueue queueIds : " + queueIds);
        queueRepository.updateQueueStatusByIds(modifyDt, QueueStatus.PROCESSING, queueIds);

    }
}
