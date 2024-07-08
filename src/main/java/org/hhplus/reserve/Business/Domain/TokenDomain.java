package org.hhplus.reserve.Business.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class TokenDomain {
    Integer tokenId;
    Integer userId;

    public TokenResponseDTO toDTO()
    {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        BeanUtils.copyProperties(this,tokenResponseDTO);
        return  tokenResponseDTO;
    }
}
