package org.hhplus.reserve.Business.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.reserve.Business.Enum.QueueStatus;
import org.hhplus.reserve.Presentation.DTO.Concert.ConcertResponseDTO;
import org.hhplus.reserve.Presentation.DTO.Queue.QueueResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueueDomain {
    private Integer queueId;
    private UUID userUUID;
    private QueueStatus queueStatus;
    private String create_dt;
    private String modify_dt;

    public QueueResponseDTO toDTO()
    {
        QueueResponseDTO queueResponseDTO = new QueueResponseDTO();
        BeanUtils.copyProperties(this, queueResponseDTO);
        return queueResponseDTO;
    }
}
