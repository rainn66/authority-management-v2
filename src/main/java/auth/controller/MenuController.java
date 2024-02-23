package auth.controller;

import auth.entity.Authority;
import auth.entity.Menu;
import auth.repository.AuthorityRepository;
import auth.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuRepository menuRepository;

    private final AuthorityRepository authorityRepository;

    @GetMapping("/menuMng")
    public String menuMng(Model model) {
        List<Menu> menus = menuRepository.findByParentIsNull();

        List<Authority> authorities = authorityRepository.findAll();

        model.addAttribute("menus", menus);
        model.addAttribute("authorities", authorities);
        return "menu/menu_mng";
    }

    @ResponseBody
    @PostMapping("/menuInfo")
    public Map<String, Object> menuInfo(@RequestBody Map<String, Object> param) {
        log.info("param : {}", param);
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Optional<Menu> menuInfo = menuRepository.findById(Long.parseLong(String.valueOf(param.get("menuIdx"))));

        rtnMap.put("menuInfo", menuInfo);

        return rtnMap;
    }

}
