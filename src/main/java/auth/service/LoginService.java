package auth.service;

import auth.dto.AdminDTO;
import auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final AdminRepository adminRepository;

    @Transactional
    public AdminDTO login(String userId, String password) {
        return adminRepository.findByUserId(userId)
                .filter(admin -> admin.getPassword().equals(password))
                .map(admin -> {
                    admin.update(LocalDateTime.now());
                    return admin;
                })
                .map(AdminDTO::new).orElse(null);
    }

}
