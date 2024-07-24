package org.hhplus.reserve.Business.Repository;

import org.hhplus.reserve.Business.Domain.TokenDomain;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository {

    TokenDomain save(Integer userId);
    Optional<Integer> exist(Integer userId);
    TokenDomain select(Integer userId);
    void delete(Integer userId);
}
