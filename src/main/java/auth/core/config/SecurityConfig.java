package auth.core.config;

import auth.core.filter.LoginFailureHandler;
import auth.core.filter.LoginSuccessHandler;
import auth.core.security.CustomAdminDetailsService;
import auth.core.security.MenuAuthority;
import auth.dto.MenuDTO;
import auth.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private final CustomAdminDetailsService customAdminDetailsService;

    private final MenuService menuService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        matchUrlAuthority(http);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) //iframe(SameOrigin) 허용

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**", "/common/**", "/*.ico", "/error", "/login").permitAll()
                        .anyRequest().authenticated())

                .formLogin(f -> f
                        .usernameParameter("userId")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(new LoginSuccessHandler(menuService))
                        .failureHandler(new LoginFailureHandler()))

                .sessionManagement(s -> s
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login"))

                .rememberMe(r -> r
                        .rememberMeParameter("rememberMe")
                        .tokenValiditySeconds(604800)
                        .alwaysRemember(false)
                        .userDetailsService(customAdminDetailsService));

        return http.build();
    }

    private void matchUrlAuthority(HttpSecurity http) throws Exception {
        if (MenuAuthority.menuAuthorities.size() > 0) {
            HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            if (req.getHeader("nowURL") == null) {
                String authorityUrl = req.getHeader("nowURL");

                for (MenuDTO menu : MenuAuthority.menuAuthorities) {
                    if (menu.getMenuLink().equals(authorityUrl)) {
                        http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**/update**", "/**/save**", "/**/delete**").hasAnyAuthority(menu.getSaveAuthority()));
                    }
                }
            }
        }
    }

}
