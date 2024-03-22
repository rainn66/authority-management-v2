package auth.controller;

import auth.core.exception.MessageException;
import auth.core.util.CustomMap;
import auth.dto.AdminDTO;
import auth.dto.AdminRegDTO;
import auth.service.AdminService;
import auth.service.AuthorityService;
import auth.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 권한관리 (관리자 권한 관리, 메뉴 권한 일괄관리)
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    private final AdminService adminService;

    private final MenuService menuService;

    /**
     * 관리자 목록 조회
     */
    @GetMapping("/admin")
    public String getAdminList(Model model) throws Exception {
        try {
            model.addAttribute("adminList", adminService.getAdminList());
            model.addAttribute("authorities", authorityService.getAuthorityList());
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "auth/auth_mng";
    }

    /**
     * 관리자 정보 조회
     */
    @ResponseBody
    @GetMapping("/admin/{adminIdx}")
    public Map<String, Object> getAdminInfo(@PathVariable Long adminIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            rtnMap.put("adminInfo", adminService.getAdminInfo(adminIdx));
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 관리자 신규등록
     */
    @ResponseBody
    @PostMapping("/admin")
    public Map<String, Object> saveAdmin(@RequestBody @Valid AdminRegDTO adminDTO) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            adminService.saveAdmin(adminDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }


    /**
     * 관리자 정보 수정(관리자명, 권한)
     */
    @ResponseBody
    @PostMapping("/admin/{adminIdx}")
    public Map<String, Object> updateAdmin(@PathVariable Long adminIdx, @RequestBody @Valid AdminDTO adminDTO, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            adminDTO.setAdminIdx(adminIdx);
            adminService.updateAdmin(adminDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    /**
     * 관리자 삭제
     */
    @ResponseBody
    @DeleteMapping("/admin/{adminIdx}")
    public Map<String, Object> deleteAdmin(@PathVariable Long adminIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            adminService.deleteAdmin(adminIdx);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "삭제되었습니다.");
        return rtnMap;
    }

    /**
     * 관리자 신규등록 - 아이디 중복체크
     */
    @ResponseBody
    @PostMapping("/admin/checkId")
    public Map<String, Object> checkId(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            AdminDTO admin = adminService.checkIdDuplication(param.getStr("userId"));
            rtnMap.put("checkUser", admin);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 메뉴 목록 (메뉴권한 일괄 관리)
     */
    @GetMapping("/menu")
    public String getMenuAll(Model model) throws Exception {
        try {
            model.addAttribute("menuList", menuService.getMenuList());
            model.addAttribute("authorities", authorityService.getAuthorityList());
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "auth/menu_auth_mng";
    }

    /**
     * 메뉴 권한 일괄 저장
     */
    @ResponseBody
    @PostMapping("/menu/all")
    public Map<String, Object> updateMenuAuth(@RequestBody List<CustomMap> param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            log.info("paramList : {}", param);

            menuService.updateAllMenuAuth(param);

        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }
}
