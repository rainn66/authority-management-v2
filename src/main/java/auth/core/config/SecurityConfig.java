package auth.core.config;

import auth.core.filter.AuthorityCheckFilter;
import auth.core.filter.LoginFailureHandler;
import auth.core.filter.LoginSuccessHandler;
import auth.core.security.CustomAdminDetailsService;
import auth.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
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

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) //iframe(SameOrigin) 허용

                //https://docs.spring.io/spring-security/reference/6.2-SNAPSHOT/servlet/authorization/authorize-http-requests.html
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**", "/common/**", "/*.ico", "/error", "/login").permitAll()
                        //동적 url 등록/수정/삭제 권한 체크
                        .anyRequest().access((authentication, context) ->
                                new AuthorizationDecision(AuthorityCheckFilter.check(authentication.get(), context.getRequest()))))

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


}
