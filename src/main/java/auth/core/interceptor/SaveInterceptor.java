package auth.core.interceptor;

import auth.core.exception.MessageException;
import auth.core.session.SessionConstants;
import auth.dto.AdminDTO;
import auth.dto.MenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SaveInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> authorityCdList = new ArrayList<>();
        try {
            if (request.getHeader("nowURL") == null) {
                return true;
            }

            String authorityUrl = request.getHeader("nowURL");
            log.info("SaveInterceptor : [{}]", authorityUrl);
            HttpSession session = request.getSession();
            MenuDTO menuDTO = (MenuDTO) session.getAttribute(authorityUrl);
            AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConstants.LOGIN_ADMIN);

            String authorityCd = adminDTO.getAuthorityCd();

            authorityCdList.add("AUTH001");
            if ("AUTH002".equals(authorityCd)) {
                authorityCdList.add("AUTH002");
            } else if ("AUTH003".equals(authorityCd)) {
                authorityCdList.add("AUTH002");
                authorityCdList.add("AUTH003");
            }
            if (authorityCdList.contains(menuDTO.getSaveAuthority())) {
                return true;
            } else {
                throw new MessageException("등록/수정/삭제 권한이 없습니다. 관리자에게 문의하세요.");
            }
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e.getMessage());
        }
    }
}
