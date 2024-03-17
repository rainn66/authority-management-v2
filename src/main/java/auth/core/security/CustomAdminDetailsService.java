package auth.core.security;

import auth.entity.Admin;
import auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        log.info("userId : {}", userId);
        Admin admin = adminRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다.")
        );
        return new CustomAdminDetails(admin);
    }
}
