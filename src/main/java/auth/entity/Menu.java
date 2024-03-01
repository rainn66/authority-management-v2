package auth.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends CommonBase {

    @Id
    @GeneratedValue
    private Long menuIdx;

    private Long menuOrder;
    private String menuNm;
    private String menuLink;

    private Long viewAuthority;

    private Long saveAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_idx")
    @Nullable
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Menu> childMenu;

    public void update(Long menuOrder, String menuNm, String menuLink, Long viewAuthority, Long saveAuthority, Menu parent) {
        this.menuOrder = menuOrder;
        this.menuNm = menuNm;
        this.menuLink = menuLink;
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
        this.parent = parent;
    }
    public void update(String menuNm, String menuLink, Long viewAuthority, Long saveAuthority) {
        this.menuNm = menuNm;
        this.menuLink = menuLink;
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
    }

}
