package org.hhplus.reserve.Business.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.QueueService;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    public QueueResponseDTO applyQueue(UUID userUuid){
        return null;
    }

}
