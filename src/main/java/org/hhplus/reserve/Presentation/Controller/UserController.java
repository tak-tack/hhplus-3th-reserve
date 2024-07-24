package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Usecase.Facade.UserFacade;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;
    /*
    토큰 인증 발급 API
     */
    @PostMapping("/authentication")
    public TokenResponseDTO authentication(@RequestBody TokenRequestDTO tokenRequestDTO){
        return userFacade.AuthenticationApplication(tokenRequestDTO);
    }
// 코
}