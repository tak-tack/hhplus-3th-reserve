package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.*;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final TokenService tokenService;

    public TokenResponseDTO AuthenticationApplication(TokenRequestDTO tokenRequestDTO){
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }



}
