package auth.controller;

import auth.core.session.SessionConstants;
import auth.core.util.EncryptUtil;
import auth.dto.AdminDTO;
import auth.dto.LoginDTO;
import auth.dto.MenuDTO;
import auth.service.LoginService;
import auth.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 로그인 관련 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final MenuService menuService;

    /**
     * 로그인페이지
     */
    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginDTO from) {
        return "login/login";
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginDTO form,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request
                        ) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult);
            return "login/login";
        }

        AdminDTO adminDTO = loginService.login(form.getUserId(), EncryptUtil.passwordEncode(form.getPassword()));

        if (adminDTO == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 바르지 않습니다.");
            return "login/login";
        }

        HttpSession session = request.getSession();

        List<MenuDTO> menuList = menuService.getMenuList();
        for (MenuDTO menuDTO : menuList) {
            if (menuDTO.getMenuLink() != null) {
                session.setAttribute(menuDTO.getMenuLink(), menuDTO);
            }
        }
        session.setAttribute(SessionConstants.LOGIN_ADMIN, adminDTO);

        return "redirect:" + redirectURL;
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


}
