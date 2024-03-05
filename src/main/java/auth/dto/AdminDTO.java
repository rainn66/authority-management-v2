package auth.dto;

import auth.entity.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDTO {
    private String userId;

    private String userNm;

    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDt;

    public AdminDTO(Admin admin) {
        this.userId = admin.getUserId();
        this.userNm = admin.getUserNm();
        this.password = admin.getPassword();
        this.lastLoginDt = admin.getLastLoginDt();
    }
}
