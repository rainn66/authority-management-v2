package auth.controller;

import auth.core.exception.MessageException;
import auth.core.util.CustomMap;
import auth.dto.AdminDTO;
import auth.dto.AdminRegDTO;
import auth.service.AdminService;
import auth.service.AuthorityService;
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
 * 관리자 등록, 수정, 삭제 관리 / 관리자 권한관리 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    private final AdminService adminService;

    @GetMapping("/getAdminList")
    public String getAdminList(Model model) {

        model.addAttribute("adminList", adminService.getAdminList());
        model.addAttribute("authorities", authorityService.getAuthorityList());
        return "auth/auth_mng";
    }

    @ResponseBody
    @PostMapping("/getAdminInfo")
    public Map<String, Object> getAdminInfo(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            rtnMap.put("adminInfo", adminService.getAdminInfo(param.getLong("adminIdx")));
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/saveAdmin")
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

    @ResponseBody
    @PostMapping("/updateAdmin")
    public Map<String, Object> updateAdmin(@RequestBody @Valid AdminDTO adminDTO, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            adminService.updateAdmin(adminDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/deleteAdmin")
    public Map<String, Object> deleteAdmin(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            adminService.deleteAdmin(param.getLong("adminIdx"));
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "삭제되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/checkId")
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
}
