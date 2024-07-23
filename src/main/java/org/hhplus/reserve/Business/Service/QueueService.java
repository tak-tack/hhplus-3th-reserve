package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;

import java.util.List;
import java.util.UUID;

public interface QueueService {
    List<QueueResponseDTO> applyQueue(Integer userId);
    List<QueueResponseDTO> checkQueue(Integer userId);
    void updateQueue();
}
