package org.hhplus.reserve.Business.Usecase.Facade;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Domain.User.TokenService;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final TokenService tokenService;

    public TokenResponseDTO authenticationApplication(TokenRequestDTO tokenRequestDTO){
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }

}
