package org.hhplus.reserve.Business.Domain.User.model;

import lombok.Getter;
import lombok.Setter;
import org.hhplus.reserve.Interface.DTO.Token.TokenResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
public class TokenDomain {
    private String user_UUID;
    private Integer userId;
    private String create_dt;

    public TokenResponseDTO toDTO()
    {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        BeanUtils.copyProperties(this,tokenResponseDTO);
        return  tokenResponseDTO;
    }
    public String setUser_Uuid(UUID user_UUID){
        return this.user_UUID = user_UUID.toString();
    }
}
