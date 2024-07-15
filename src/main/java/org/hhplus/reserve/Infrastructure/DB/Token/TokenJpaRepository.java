package org.hhplus.reserve.Infrastructure.DB.Token;

import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, UUID> {

    @Query("select t from TokenEntity t where t.userId = :userId")
    TokenEntity findByUserId(@Param("userId") Integer userId);
    boolean existsByUserId(@Param("userId") Integer userId);
}