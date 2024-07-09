package org.hhplus.reserve.Business.Application;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.ConcertService;
import org.hhplus.reserve.Business.Service.Impl.TokenService;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ConcertServiceFacade {
    private final TokenService tokenService;
    //private final ConcertService concertService; 외않돼

    public TokenResponseDTO AuthenticationApplication(TokenRequestDTO tokenRequestDTO){
        //tokenService.checkAuth(tokenRequestDTO.getUserId());
        return this.tokenService.applyAuth(tokenRequestDTO.getUserId());
    }


}
