package auth.controller;

import auth.dto.AdminDTO;
import auth.dto.AdminRegDTO;
import auth.service.AdminService;
import auth.service.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public Map<String, Object> getAdminInfo(@RequestBody Map<String, Object> param) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("adminInfo", adminService.getAdminInfo(Long.parseLong(String.valueOf(param.get("adminIdx")))));
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/saveAdmin")
    public Map<String, Object> saveAdmin(@RequestBody @Valid AdminRegDTO adminDTO) {
        Map<String, Object> rtnMap = new HashMap<>();
        adminService.saveAdmin(adminDTO);
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/updateAdmin")
    public Map<String, Object> updateAdmin(@RequestBody @Valid AdminDTO adminDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldErrors().get(0);
            throw new Exception(error.getDefaultMessage());
        }
        Map<String, Object> rtnMap = new HashMap<>();
        adminService.updateAdmin(adminDTO);
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/deleteAdmin")
    public Map<String, Object> deleteAdmin(@RequestBody Map<String, Object> param) {
        Map<String, Object> rtnMap = new HashMap<>();
        adminService.deleteAdmin(Long.parseLong(String.valueOf(param.get("adminIdx"))));
        rtnMap.put("message", "삭제되었습니다.");
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/checkId")
    public Map<String, Object> checkId(@RequestBody Map<String, Object> param) {
        Map<String, Object> rtnMap = new HashMap<>();
        AdminDTO admin = adminService.checkIdDuplication(String.valueOf(param.get("userId")));
        rtnMap.put("checkUser", admin);
        return rtnMap;
    }
}
