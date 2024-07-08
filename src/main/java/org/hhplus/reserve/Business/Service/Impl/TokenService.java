package org.hhplus.reserve.Business.Service.Impl;

import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.hhplus.reserve.Presentation.DTO.TokenRequestDTO;


public interface TokenService {

    public TokenDomain checkAuth(TokenRequestDTO tokenRequestDTO);

}
