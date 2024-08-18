package org.hhplus.reserve.Interface.DTO.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.reserve.Business.Enum.QueueStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponseDTO {
    private Integer queueId;
    private UUID userUUID;
    private QueueStatus queueStatus;
    private String create_dt;
    private String modify_dt;

}
