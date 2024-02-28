package auth.dto;

import auth.entity.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenuDTO {
    private Long menuIdx;
    private Long menuOrder;
    private String menuNm;
    private String menuLink;
    private Long viewAuthority;
    private Long saveAuthority;
    private Long parentMenuIdx;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    public MenuDTO(Long menuIdx, Long menuOrder, String menuNm, String menuLink, Long viewAuthority, Long saveAuthority, Menu parent, LocalDateTime regDt) {
        this.menuIdx = menuIdx;
        this.menuOrder = menuOrder;
        this.menuNm = menuNm;
        this.menuLink = menuLink;
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
        if (parent == null) {
            this.parentMenuIdx = null;
        } else {
            this.parentMenuIdx = parent.getMenuIdx();
        }
        this.regDt = regDt;
    }
}