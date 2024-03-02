package auth.controller;

import auth.dto.MenuDTO;
import auth.service.AuthorityService;
import auth.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    private final AuthorityService authorityService;


    @GetMapping("/getMenuList")
    public String getMenuList(Model model) {
        model.addAttribute("menus", menuService.getMenuList());
        model.addAttribute("authorities", authorityService.getAuthorityList());
        return "menu/menu_mng";
    }

    @ResponseBody
    @PostMapping("/getMenuInfo")
    public Map<String, Object> getMenuInfo(@RequestBody Map<String, Object> param) {
        log.info("param : {}", param);
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("menuInfo", menuService.getMenuInfo(Long.parseLong(String.valueOf(param.get("menuIdx")))));
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/saveMenu")
    public Map<String, Object> saveMenu(@RequestBody @Valid MenuDTO menuDTO, BindingResult bindingResult) throws Exception {
        Map<String, Object> rtnMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldErrors().get(0);
            throw new Exception(error.getDefaultMessage());
        }

        menuService.saveMenu(menuDTO);

        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/updateMenu")
    public Map<String, Object> updateMenu(@RequestBody @Valid MenuDTO menuDTO, BindingResult bindingResult) throws Exception {
        Map<String, Object> rtnMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldErrors().get(0);
            throw new Exception(error.getDefaultMessage());
        }

        menuService.updateMenu(menuDTO);

        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }


}
