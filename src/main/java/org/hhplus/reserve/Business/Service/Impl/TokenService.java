package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;
import org.hhplus.reserve.Presentation.DTO.TokenResponseDTO;

import java.time.LocalDateTime;


public interface TokenService {

    TokenResponseDTO applyAuth(Integer userId);

    TokenResponseDTO checkAuth(Integer userId);

}
