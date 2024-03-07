package auth.service;

import auth.dto.AdminDTO;
import auth.dto.AdminRegDTO;
import auth.entity.Admin;
import auth.entity.Authority;
import auth.repository.AdminRepository;
import auth.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final AuthorityRepository authorityRepository;

    public List<AdminDTO> getAdminList() {
        return adminRepository.findAll().stream().map(AdminDTO::new).collect(Collectors.toList());
    }

    public AdminDTO getAdminInfo(Long adminIdx) {
        return adminRepository.findById(adminIdx).map(AdminDTO::new).orElseThrow();
    }

    @Transactional
    public void saveAdmin(AdminRegDTO adminDTO) {
        Authority authority = authorityRepository.findById(adminDTO.getAuthorityIdx()).orElseThrow();

        Admin newAdmin = Admin.builder()
                .userId(adminDTO.getUserId())
                .userNm(adminDTO.getUserNm())
                .password(adminDTO.getPassword())
                .authority(authority)
                .build();
        adminRepository.save(newAdmin);
    }

    @Transactional
    public void updateAdmin(AdminDTO adminDTO) {
        Authority authority = authorityRepository.findById(adminDTO.getAuthorityIdx()).orElseThrow();
        Admin findAdmin = adminRepository.findById(adminDTO.getAdminIdx()).orElseThrow();
        findAdmin.update(adminDTO.getUserNm(), authority);
    }

    @Transactional
    public void deleteAdmin(Long adminIdx) {
        adminRepository.deleteById(adminIdx);
    }

    //아이디중복체크
    public AdminDTO checkIdDuplication(String userId) {
        return adminRepository.findByUserId(userId).map(AdminDTO::new).orElse(null);
    }
}
