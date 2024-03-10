package auth;

import auth.dto.AdminDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class AuthorityManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityManagementApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
			if (RequestContextHolder.getRequestAttributes() != null) {
				HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				HttpSession session = req.getSession();
				if (session.getAttribute("loginAdmin") != null) {
					AdminDTO currentUser = (AdminDTO) session.getAttribute("loginAdmin");
					return Optional.of(currentUser.getUserId());
				} else {
					return Optional.of("Anonymous");
				}
			} else {
				return Optional.of("Anonymous");
			}
		};
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}
