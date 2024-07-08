package org.hhplus.reserve.Business.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TokenDomain {
    private UUID user_UUID;
    private Integer userId;
    private String create_dt;

    public TokenResponseDTO toDTO()
    {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        BeanUtils.copyProperties(this,tokenResponseDTO);
        return  tokenResponseDTO;
    }
}
