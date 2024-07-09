package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.TokenDomain;

import java.time.LocalDateTime;

public interface TokenRepository {

    TokenDomain save(Integer userId);
    boolean exist(Integer userId);
    TokenDomain select(Integer userId);
}
