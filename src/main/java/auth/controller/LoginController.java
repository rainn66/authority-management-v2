package auth.controller;

import auth.dto.AdminDTO;
import auth.dto.LoginDTO;
import auth.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginDTO form,
                        HttpServletRequest request,
                        BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        AdminDTO adminDTO = loginService.login(form.getUserId(), form.getPassword());

        if (adminDTO == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 바르지 않습니다.");
            return "login/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginAdmin", adminDTO);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }



}
