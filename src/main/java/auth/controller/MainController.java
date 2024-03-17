package auth.controller;

import auth.core.security.CustomAdminDetails;
import auth.dto.MenuDTO;
import auth.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
    public String home(Model model) throws Exception {
        try {
            CustomAdminDetails principal = (CustomAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<MenuDTO> parentMenu = menuService.getSideMenuList(principal.getAuthoritiesStrArr());
            model.addAttribute("sidebar", parentMenu);
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "index";
    }
}
