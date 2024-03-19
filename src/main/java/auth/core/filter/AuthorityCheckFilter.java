package auth.core.filter;

import auth.core.security.CustomAdminDetails;
import auth.core.security.MenuAuthority;
import auth.dto.MenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class AuthorityCheckFilter {

    private static final String[] notFilteringList = {"/**/update**", "/**/save**", "/**/delete**"};

    public static boolean check(Authentication authentication, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        log.info("AuthorityCheckFilter.check requestUri : {}", requestUri);

        if (authentication.getName().equals("anonymousUser")) {
            log.info("AuthorityCheckFilter.check authentication is null");
            return false;
        }

        if (PatternMatchUtils.simpleMatch(notFilteringList, requestUri)) {
            String nowUrl = request.getHeader("nowURL");
            if (nowUrl == null) {
                log.info("AuthorityCheckFilter.check nowUrl is null");
                return false;
            }
            log.info("AuthorityCheckFilter.check nowUrl : {}", nowUrl);
            CustomAdminDetails loginAdmin = (CustomAdminDetails) authentication.getPrincipal();
            for (MenuDTO menuDTO: MenuAuthority.menuAuthorities) {
                if (menuDTO.getMenuLink() != null && menuDTO.getMenuLink().equals(nowUrl)) {
                    log.info("AuthorityCheckFilter.check loginAdmin.getAuthoritiesStrArr() : {}", loginAdmin.getAuthoritiesStrArr());
                    log.info("AuthorityCheckFilter.check menuDTO.getSaveAuthority() : {}", menuDTO.getSaveAuthority());
                    return loginAdmin.getAuthoritiesStrArr().contains(menuDTO.getSaveAuthority());
                }
            }
        }
        return true;
    }
}
