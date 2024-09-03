package org.hhplus.reserve.Infrastructure.DB.Token;

import org.hhplus.reserve.Business.Domain.User.model.TokenDomain;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {

    TokenDomain save(UUID userUuid);
    //Optional<Integer> exist(Integer userId);
    Optional<UUID> exist(String userString);
    TokenDomain select(UUID userString);
    void delete(UUID userString);
}
