package auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminRegDTO {

    @NotEmpty
    @Length(min = 4, max = 20)
    private String userId;

    @NotEmpty
    private String userNm;

    @NotEmpty
    @Length(min = 4, max = 20)
    private String password;

    @NotNull
    private String authorityCd;
}
