package auth.dto;

import auth.entity.Authority;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityDTO {
    private Long authorityIdx;
    @NotEmpty
    private String authorityNm;
    private String regUserId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;
    public AuthorityDTO(Authority authority) {
        this.authorityIdx = authority.getAuthorityIdx();
        this.authorityNm = authority.getAuthorityNm();
        this.regUserId = authority.getRegUserId();
        this.regDt = authority.getRegDt();
    }
}
