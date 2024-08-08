package org.hhplus.reserve.Infrastructure.DB.Token;

import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, UUID> {

    @Query("select t from TokenEntity t where t.userId = :userId")
    TokenEntity findByUserId(@Param("userId") Integer userId);

    //
    //@Query("SELECT t.userId FROM TokenEntity t WHERE t.userId = :userId ORDER BY t.userId ASC")
    @Query("select t.userId from TokenEntity t where t.userId = :userId")
    Optional<Integer> existsByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.userId =:userId")
    void deleteByUserId(@Param("userId")Integer userId);

}