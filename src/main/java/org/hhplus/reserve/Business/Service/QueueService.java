package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;

import java.util.UUID;

public interface QueueService {
    QueueResponseDTO applyQueue(UUID userUuid);
}
