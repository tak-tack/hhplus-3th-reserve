package org.hhplus.reserve.Business.Service;

import org.hhplus.reserve.Presentation.DTO.Token.TokenResponseDTO;


public interface TokenService {

    TokenResponseDTO applyAuth(Integer userId);

    TokenResponseDTO checkAuth(Integer userId);

}
