package org.hhplus.reserve.Interface.DTO.Token;
import lombok.*;

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
