package auth.core.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String msg = "";
        if (exception instanceof BadCredentialsException) {
            msg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            msg = exception.getMessage();
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            msg = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            msg = "서버 에러. 관리자에게 문의하세요.";
        }
        setDefaultFailureUrl("/login?errorMsg="+URLEncoder.encode(msg, StandardCharsets.UTF_8));
        super.onAuthenticationFailure(request, response, exception);
    }
}
