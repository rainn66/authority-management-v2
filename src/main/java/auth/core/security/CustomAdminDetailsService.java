package auth.core.security;

import auth.core.exception.MessageException;
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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("userId : {}", userId);
        Admin admin = adminRepository.findByUserId(userId).orElseThrow(
                () -> new MessageException("인증 실패")
        );
        return new CustomAdminDetails(admin);
    }
}
