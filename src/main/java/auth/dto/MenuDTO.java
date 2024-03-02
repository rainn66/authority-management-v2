package auth.dto;

import auth.entity.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
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

    //상위 메뉴 변경 구분값
    private String parentChangeYn;

    private Long parentMenuIdx;

    private List<MenuDTO> childMenu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    public MenuDTO(Menu menu) {
        this.menuIdx = menu.getMenuIdx();
        this.menuOrder = menu.getMenuOrder();
        this.menuNm = menu.getMenuNm();
        this.menuLink = menu.getMenuLink();
        this.viewAuthority = menu.getViewAuthority();
        this.saveAuthority = menu.getSaveAuthority();
        this.parentMenuIdx = menu.getParent() == null ? null : menu.getParent().getMenuIdx();
        this.childMenu = menu.getChildMenu().stream().map(MenuDTO::new).collect(Collectors.toList());
        this.regDt = menu.getRegDt();
    }
}