package auth.controller;

import auth.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * request url 명명규칙
 * 1. view, list 조회는 화면이 수행하는 총체적인 기능 중심으로
 * 2. save, update, delete 해당 기능 중심으로 작성
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MenuService menuService;

    /**
     * 메인화면
     */
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) throws Exception {
//        try {
//            HttpSession session = request.getSession();
//            AdminDTO loginAdmin = (AdminDTO) session.getAttribute(SessionConstants.LOGIN_ADMIN);
//            List<MenuDTO> parentMenu = menuService.getSideMenuList(loginAdmin.getAuthorityCd());
//            model.addAttribute("sidebar", parentMenu);
//        } catch (Exception e) {
//            log.error("", e);
//            throw new Exception(e);
//        }
        return "index";
    }
}
