package auth.core.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authorityCheckFilter")
public class AuthorityCheckFilter {

    public boolean check(HttpServletRequest request, Authentication authentication) throws Exception{
        return true;
    }

}
