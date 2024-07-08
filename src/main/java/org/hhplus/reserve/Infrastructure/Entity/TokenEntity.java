package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.TokenDomain;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Setter
@Getter
@Builder
@Entity
@Table(name="token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_UUID;
    private Integer userId;
    @CreatedDate
    private String create_dt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.create_dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public TokenDomain toDomain()
    {
        TokenDomain tokenDomain = new TokenDomain();
        BeanUtils.copyProperties(this,tokenDomain);
        return tokenDomain;

    }
}


