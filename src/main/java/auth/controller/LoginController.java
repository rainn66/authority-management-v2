package auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 로그인 관련 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    /**
     * 로그인페이지
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "errorMsg", required = false) String errorMsg, Model model) {
        model.addAttribute("errorMsg", errorMsg);
        return "login/login";
    }

}
