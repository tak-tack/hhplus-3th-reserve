package org.hhplus.reserve.Infrastructure.DB.Token;

import org.hhplus.reserve.Infrastructure.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, UUID> {

    @Query("select t from TokenEntity t where t.user_UUID= :userUuid")
    TokenEntity findByUserId(@Param("userUuid") UUID userUuid);

    @Query(value = "SELECT * FROM dba.token t WHERE t.user_UUID = UNHEX(REPLACE(:userUuid, '-', ''))", nativeQuery = true)
    Optional<UUID> existsByUserUUID(@Param("userUuid") String userUuid);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.user_UUID= :userUuid")
    void deleteByUserId(@Param("userUuid")UUID userUuid);

}