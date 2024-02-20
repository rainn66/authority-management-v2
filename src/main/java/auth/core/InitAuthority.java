package auth.core;

import auth.entity.Authority;
import auth.entity.Menu;
import auth.repository.AuthorityRepository;
import auth.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitAuthority {

    private final AuthorityRepository authorityRepository;

    private final MenuRepository menuRepository;

    @PostConstruct
    public void initAuthority() {

        Authority 시스템관리자 = Authority.builder().authorityNm("시스템관리자").build();
        Authority 일반관리자 = Authority.builder().authorityNm("일반관리자").build();
        Authority 일반사용자 = Authority.builder().authorityNm("일반사용자").build();

        authorityRepository.save(시스템관리자);
        authorityRepository.save(일반관리자);
        authorityRepository.save(일반사용자);



        for (int i = 0; i < 5; i++) {
            Menu menu = Menu.builder().menuNm("최상위 메뉴 " + (i + 1))
                    .menuOrder(Long.parseLong(String.valueOf(i + 1)))
                    .viewAuthority("ALL")
                    .saveAuthority("ALL")
                    .deleteAuthority("ALL").build();

            menuRepository.save(menu);

            if (i == 0 || i == 3) {
                for (int j = 0; j < 3; j++) {
                    Menu lowMenu = Menu.builder().menuNm("하위 메뉴 " + (j + 1))
                            .parent(menu)
                            .menuOrder(Long.parseLong(String.valueOf(j + 1)))
                            .menuLink("/temp/low/" + (j + 1))
                            .viewAuthority("ALL")
                            .saveAuthority("ALL")
                            .deleteAuthority("ALL").build();
                    menuRepository.save(lowMenu);
                }
            }
        }

        Menu menu = Menu.builder().menuNm("메뉴관리")
                .menuOrder(6L)
                .menuLink("/menu/menu_mng.html")
                .viewAuthority("ALL")
                .saveAuthority("ALL")
                .deleteAuthority("ALL").build();

        menuRepository.save(menu);

    }

}
