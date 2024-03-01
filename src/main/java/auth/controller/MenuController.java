package auth.controller;

import auth.dto.MenuDTO;
import auth.entity.Authority;
import auth.entity.Menu;
import auth.repository.AuthorityRepository;
import auth.repository.MenuRepository;
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

    @GetMapping("/getMenuList")
    public String getMenuList(Model model) {
        List<Menu> menus = menuRepository.findByParentIsNull();

        List<Authority> authorities = authorityRepository.findAll();

        model.addAttribute("menus", menus);
        model.addAttribute("authorities", authorities);
        return "menu/menu_mng";
    }

    @ResponseBody
    @PostMapping("/getMenuInfo")
    public Map<String, Object> getMenuInfo(@RequestBody Map<String, Object> param) {
        log.info("param : {}", param);
        Map<String, Object> rtnMap = new HashMap<>();
        Menu menuInfo = menuRepository.findById(Long.parseLong(String.valueOf(param.get("menuIdx")))).orElseThrow();

        MenuDTO menuDTO = new MenuDTO(menuInfo.getMenuIdx(), menuInfo.getMenuOrder(), menuInfo.getMenuNm(),
                menuInfo.getMenuLink(), menuInfo.getViewAuthority(), menuInfo.getSaveAuthority(),
                menuInfo.getParent() == null ? null : menuInfo.getParent().getMenuIdx(),
                menuInfo.getRegDt());

        rtnMap.put("menuInfo", menuDTO);
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

        Menu parent;
        Long count;
        if (menuDTO.getParentMenuIdx() == null) {
            parent = null;
            count = menuRepository.countByParentIsNull();
        } else {
            parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow();
            count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
        }

        Menu menu = Menu.builder()
                .menuNm(menuDTO.getMenuNm())
                .menuLink(menuDTO.getMenuLink())
                .menuOrder(count + 1)
                .viewAuthority(menuDTO.getViewAuthority())
                .saveAuthority(menuDTO.getSaveAuthority())
                .parent(parent)
                .build();

        menuRepository.save(menu);

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

        Optional<Menu> findMenu = menuRepository.findById(menuDTO.getMenuIdx());

        findMenu.ifPresent(menu -> {
            //상위 메뉴가 이전과 달라졌을 경우
            if (menu.getParent() != null && !menu.getParent().getMenuIdx().equals(menuDTO.getParentMenuIdx())) {
                Menu parent;
                Long count;
                if (menuDTO.getParentMenuIdx() == null) {
                    parent = null;
                    count = menuRepository.countByParentIsNull();
                } else {
                    parent = menuRepository.findById(menuDTO.getParentMenuIdx()).orElseThrow();
                    count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
                }
                menu.update(count + 1,
                        menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority(),
                        parent);
            } else {
                menu.update(menuDTO.getMenuNm(),
                        menuDTO.getMenuLink(),
                        menuDTO.getViewAuthority(),
                        menuDTO.getSaveAuthority());
            }
        });

        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }


}
