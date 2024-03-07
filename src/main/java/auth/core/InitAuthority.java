package auth.core;

import auth.repository.AdminRepository;
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

    private final AdminRepository adminRepository;

    @PostConstruct
    public void initAuthority() {
//
//        //기본권한등록
//        Authority 시스템관리자 = Authority.builder().authorityNm("시스템관리자").build();
//        Authority 일반관리자 = Authority.builder().authorityNm("일반관리자").build();
//        Authority 일반사용자 = Authority.builder().authorityNm("일반사용자").build();
//
//        authorityRepository.save(시스템관리자);
//        authorityRepository.save(일반관리자);
//        authorityRepository.save(일반사용자);
//
//        //초기메뉴등록
//        Menu menu = Menu.builder().menuNm("메뉴관리")
//                .menuOrder(1)
//                .menuLink("/menu/getMenuList")
//                .viewAuthority(시스템관리자.getAuthorityIdx())
//                .saveAuthority(시스템관리자.getAuthorityIdx())
//                .build();
//        menuRepository.save(menu);
//
//        Menu authority = Menu.builder().menuNm("권한관리")
//                .menuOrder(2)
//                .menuLink("/authority/getAdminList")
//                .viewAuthority(일반사용자.getAuthorityIdx())
//                .saveAuthority(일반사용자.getAuthorityIdx())
//                .build();
//        menuRepository.save(authority);
//
//        Menu authorityAdmin = Menu.builder().menuNm("관리자/관리자권한관리")
//                .menuOrder(1)
//                .menuLink("/authority/getAdminList")
//                .viewAuthority(일반사용자.getAuthorityIdx())
//                .saveAuthority(일반사용자.getAuthorityIdx())
//                .parent(authority)
//                .build();
//        menuRepository.save(authorityAdmin);
//
//        Menu authorityMenu = Menu.builder().menuNm("메뉴권한관리")
//                .menuOrder(2)
//                .menuLink("/authority/getMenuList")
//                .viewAuthority(일반사용자.getAuthorityIdx())
//                .saveAuthority(일반사용자.getAuthorityIdx())
//                .parent(authority)
//                .build();
//        menuRepository.save(authorityMenu);
//
//        //초기관리자 등록
//        Admin admin = Admin.builder().userId("admin")
//                .userNm("최고관리자")
//                .password("admin")
//                .lastLoginDt(LocalDateTime.now())
//                .authority(시스템관리자).build();
//        adminRepository.save(admin);
    }

}
