package org.hhplus.reserve.Interface.DTO.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueRequestDTO {
    private UUID userUUID;

}
