package auth.core;

import auth.entity.Admin;
import auth.entity.Authority;
import auth.entity.Menu;
import auth.repository.AdminRepository;
import auth.repository.AuthorityRepository;
import auth.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitAuthority {

    private final AuthorityRepository authorityRepository;

    private final MenuRepository menuRepository;

    private final AdminRepository adminRepository;

    @PostConstruct
    public void initAuthority() {

        Authority 모든사용자 = Authority.builder().authorityNm("모든사용자").build();
        Authority 시스템관리자 = Authority.builder().authorityNm("시스템관리자").build();
        Authority 일반관리자 = Authority.builder().authorityNm("일반관리자").build();
        Authority 일반사용자 = Authority.builder().authorityNm("일반사용자").build();

        authorityRepository.save(모든사용자);
        authorityRepository.save(시스템관리자);
        authorityRepository.save(일반관리자);
        authorityRepository.save(일반사용자);

        for (int i = 0; i < 5; i++) {
            Menu menu = Menu.builder().menuNm("최상위 메뉴 " + (i + 1))
                    .menuOrder(i+1)
                    .viewAuthority(모든사용자.getAuthorityIdx())
                    .saveAuthority(모든사용자.getAuthorityIdx())
                    .menuLink("/temp/top/" + (i + 1))
                    .build();

            menuRepository.save(menu);

            if (i == 0 || i == 3) {
                for (int j = 0; j < 3; j++) {
                    Menu lowMenu = Menu.builder().menuNm("하위 메뉴 " + (j + 1))
                            .parent(menu)
                            .menuOrder(j+1)
                            .menuLink("/temp/low/" + (j + 1))
                            .viewAuthority(모든사용자.getAuthorityIdx())
                            .saveAuthority(모든사용자.getAuthorityIdx())
                            .build();
                    menuRepository.save(lowMenu);
                }
            }
        }

        Menu menu = Menu.builder().menuNm("메뉴관리")
                .menuOrder(6)
                .menuLink("/menu/getMenuList")
                .viewAuthority(모든사용자.getAuthorityIdx())
                .saveAuthority(모든사용자.getAuthorityIdx())
                .build();
        menuRepository.save(menu);

        Menu authorityMenu = Menu.builder().menuNm("권한관리")
                .menuOrder(7)
                .menuLink("/authority/getAdminList")
                .viewAuthority(모든사용자.getAuthorityIdx())
                .saveAuthority(모든사용자.getAuthorityIdx())
                .build();
        menuRepository.save(authorityMenu);

        Admin admin = Admin.builder().userId("admin")
                .userNm("최고관리자")
                .password("admin")
                .lastLoginDt(LocalDateTime.now())
                .authority(시스템관리자).build();
        adminRepository.save(admin);
    }

}
