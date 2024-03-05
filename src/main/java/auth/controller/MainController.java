package auth.controller;

import auth.dto.AdminDTO;
import auth.dto.MenuDTO;
import auth.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginAdmin", required = false) AdminDTO adminDTO, Model model) {

        if (adminDTO == null) {
            return "login/login";
        }

        List<MenuDTO> parentMenu = menuService.getMenuList();

        model.addAttribute("sidebar", parentMenu);
        return "index";
    }
}
