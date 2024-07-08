package org.hhplus.reserve.Presentation.DTO;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    public Integer tokenId;
    public Integer userId;

}
