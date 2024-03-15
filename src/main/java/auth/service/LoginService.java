package auth.service;

import auth.core.security.CustomAdminDetails;
import auth.dto.AdminDTO;
import auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AdminDTO login(String userId, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomAdminDetails customUserDetails = (CustomAdminDetails) authentication.getPrincipal();

        //마지막 로그인 날짜 업데이트
        customUserDetails.getAdmin().update(LocalDateTime.now());

        return AdminDTO.builder()
                .userId(customUserDetails.getUsername())
                .userNm(customUserDetails.getUserNm()).build();
    }

}
