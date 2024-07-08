package org.hhplus.reserve.Infrastructure.Entity;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Integer TokenId;
    Integer userId;

    public TokenDomain toDomain()
    {
        TokenDomain tokenDomain = new TokenDomain();
        BeanUtils.copyProperties(this,tokenDomain);
        return tokenDomain;

    }
}


