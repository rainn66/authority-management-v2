package auth.service;

import auth.dto.AdminDTO;
import auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;

    public AdminDTO login(String userId, String password) {
        return adminRepository.findByUserId(userId)
                .filter(admin -> admin.getPassword().equals(password))
                .map(AdminDTO::new).orElse(null);
    }

}
