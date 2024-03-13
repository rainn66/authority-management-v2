package auth.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuAuthorityDTO {
    private Long menuIdx;
    private String viewAuthority;
    private String saveAuthority;
}
