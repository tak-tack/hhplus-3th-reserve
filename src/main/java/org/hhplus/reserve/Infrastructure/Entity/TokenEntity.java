package org.hhplus.reserve.Infrastructure.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hhplus.reserve.Business.Domain.User.model.TokenDomain;
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
public class TokenEntity implements CommonEntity<TokenDomain> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_UUID;
    private Integer userId;
    @CreatedDate
    private String createDt;

    @PrePersist // 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS"));
    }

    //   domain > entity converting
        public TokenEntity(TokenDomain tokenDomain){
        BeanUtils.copyProperties(tokenDomain,this);
       }

    // entity > domain converting
    @Override
    public TokenDomain toDomain()
    {
        TokenDomain tokenDomain = new TokenDomain();
        BeanUtils.copyProperties(this,tokenDomain);
        return tokenDomain;
    }
}


