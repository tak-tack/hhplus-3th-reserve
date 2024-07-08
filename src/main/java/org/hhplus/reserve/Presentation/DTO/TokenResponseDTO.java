package org.hhplus.reserve.Presentation.DTO;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    private UUID user_UUID;
    private Integer userId;
    private String create_dt;

}
