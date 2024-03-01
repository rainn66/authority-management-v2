package auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long menuIdx;
    private Long menuOrder;

    @NotNull
    private String menuNm;

    @NotNull
    private String menuLink;

    @NotNull
    private Long viewAuthority;

    @NotNull
    private Long saveAuthority;

    @Nullable
    private Long parentMenuIdx;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;
}