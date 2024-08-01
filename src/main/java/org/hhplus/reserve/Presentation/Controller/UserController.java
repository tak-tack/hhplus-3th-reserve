package org.hhplus.reserve.Presentation.Controller;

import lombok.RequiredArgsConstructor;
import org.hhplus.reserve.Business.Service.TokenRedisService;
import org.hhplus.reserve.Business.Usecase.Facade.UserFacade;
import org.hhplus.reserve.Presentation.DTO.Token.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;
    private final TokenRedisService tokenRedisService;
    /*
토큰 저장 API
 */
    @GetMapping("/tokenSave")
    public void tokenSave(@PathVariable(name="userId") Integer userId){
        tokenRedisService.saveToken(userId.toString());
    }

    /*
    토큰 인증 발급 API
     */
    @PostMapping("/authentication")
    public TokenResponseDTO authentication(@RequestBody TokenRequestDTO tokenRequestDTO){
        return userFacade.authenticationApplication(tokenRequestDTO);
    }


}