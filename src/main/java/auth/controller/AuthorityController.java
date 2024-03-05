package auth.controller;

import auth.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("/getAuthList")
    public String getAuthList(Model model) {
        model.addAttribute("authorities", authorityService.getAuthorityList());
        return "auth/auth_mng";
    }

}
