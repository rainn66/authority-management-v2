package auth.controller;

import auth.entity.Menu;
import auth.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
