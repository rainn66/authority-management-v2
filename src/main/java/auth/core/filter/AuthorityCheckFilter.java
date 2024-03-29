package auth.core.filter;

import auth.core.exception.MessageException;
import auth.core.security.CustomAdminDetails;
import auth.core.security.MenuAuthority;
import auth.dto.MenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@Slf4j
public class AuthorityCheckFilter {

    public static boolean check(Authentication authentication, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        log.info("AuthorityCheckFilter.check requestUri : {}", requestUri);

        if (authentication.getName().equals("anonymousUser")) {
            log.info("AuthorityCheckFilter.check authentication is null");
            return false;
        }

        //request method 가 get 이 아닌 요청에 대해 필터 적용
        if (!request.getMethod().equals("GET")) {
            String nowUrl = request.getHeader("nowURL"); //비동기(ajax) 접근방식만 허용됨
            if (nowUrl == null) {
                log.info("AuthorityCheckFilter.check nowUrl is null");
                throw new MessageException("잘못된 접근입니다.");
            }
            log.info("AuthorityCheckFilter.check nowUrl : {}", nowUrl);
            CustomAdminDetails loginAdmin = (CustomAdminDetails) authentication.getPrincipal();
            for (MenuDTO menuDTO: MenuAuthority.menuAuthorities) {
                if (menuDTO.getMenuLink() != null && menuDTO.getMenuLink().equals(nowUrl)) {
                    log.info("AuthorityCheckFilter.check loginAdmin.getAuthoritiesStrArr() : {}", loginAdmin.getAuthoritiesStrArr());
                    log.info("AuthorityCheckFilter.check menuDTO.getSaveAuthority() : {}", menuDTO.getSaveAuthority());
                    if (loginAdmin.getAuthoritiesStrArr().contains(menuDTO.getSaveAuthority())) {
                        return true;
                    } else {
                        throw new MessageException("등록/수정/삭제 권한이 없습니다.");
                    }
                }
            }
        }
        return true;
    }
}
