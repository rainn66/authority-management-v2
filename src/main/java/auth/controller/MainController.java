package auth.controller;

import auth.entity.Menu;
import auth.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final MenuRepository menuRepository;

    @GetMapping("/")
    public String home(Model model) {

        List<Menu> parentMenu = menuRepository.findByParentIsNull();

        model.addAttribute("sidebar", parentMenu);
        return "index";
    }
}
