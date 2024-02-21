package auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MenuController {

    @GetMapping("/menu/menuMng")
    public String menuMng(Model model) {
        return "menu/menu_mng";
    }

}
