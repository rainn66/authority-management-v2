package auth.controller;

import auth.core.exception.MessageException;
import auth.core.util.CustomMap;
import auth.dto.MenuDTO;
import auth.service.AuthorityService;
import auth.service.MenuService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 메뉴관리
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    private final AuthorityService authorityService;

    /**
     * 메뉴 목록 조회(+권한목록)
     */
    @GetMapping("/getMenuList")
    public String getMenuList(Model model, HttpServletResponse response) throws Exception {
        //비동기 호출 아니므로 MessageException 사용불가
        try {
            model.addAttribute("menus", menuService.getMenuList());
            model.addAttribute("authorities", authorityService.getAuthorityList());
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "menu/menu_mng";
    }

    /**
     * 메뉴 정보 조회
     */
    @ResponseBody
    @PostMapping("/getMenuInfo")
    public Map<String, Object> getMenuInfo(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            rtnMap.put("menuInfo", menuService.getMenuInfo(param.getLong("menuIdx")));
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 메뉴 저장
     */
    @ResponseBody
    @PostMapping("/saveMenu")
    public Map<String, Object> saveMenu(@RequestBody @Valid MenuDTO menuDTO, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }

            menuService.saveMenu(menuDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }

        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    /**
     * 메뉴 정보 수정
     */
    @ResponseBody
    @PostMapping("/updateMenu")
    public Map<String, Object> updateMenu(@RequestBody @Valid MenuDTO menuDTO, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }

            menuService.updateMenu(menuDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    /**
     * 메뉴 삭제
     */
    @ResponseBody
    @PostMapping("/deleteMenu")
    public Map<String, Object> deleteMenu(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            menuService.deleteMenu(param.getLong("menuIdx"));
            rtnMap.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 메뉴 순서 업데이트
     */
    @ResponseBody
    @PostMapping("/setMenuOrder")
    public Map<String, Object> setMenuOrder(@RequestParam(name = "menuIdxOrder[]") Long[] menuIdxList) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            menuService.updateOrder(menuIdxList);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }


}
