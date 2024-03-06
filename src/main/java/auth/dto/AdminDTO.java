package auth.dto;

import auth.entity.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDTO {

    private Long adminIdx;

    private String userId;

    @NotEmpty
    private String userNm;

    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDt;

    @NotNull
    private Long authorityIdx;

    private String authorityNm;

    public AdminDTO(Admin admin) {
        this.adminIdx = admin.getAdminIdx();
        this.userId = admin.getUserId();
        this.userNm = admin.getUserNm();
        this.password = admin.getPassword();
        this.lastLoginDt = admin.getLastLoginDt();
        this.authorityIdx = admin.getAuthority().getAuthorityIdx();
        this.authorityNm = admin.getAuthority().getAuthorityNm();
    }
}
