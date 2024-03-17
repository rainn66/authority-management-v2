package auth.core.config;

import auth.core.filter.LoginFailureHandler;
import auth.core.filter.LoginSuccessHandler;
import auth.core.security.CustomAdminDetailsService;
import auth.service.MenuService;
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

}
