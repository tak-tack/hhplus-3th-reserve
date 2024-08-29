package org.hhplus.reserve.Business.Domain.User;

import org.hhplus.reserve.Interface.DTO.Token.TokenResponseDTO;


public interface TokenService {

    TokenResponseDTO applyAuth(Integer userId);

    TokenResponseDTO checkAuth(Integer userId);

}
