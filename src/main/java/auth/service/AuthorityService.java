package auth.service;

import auth.dto.AuthorityDTO;
import auth.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public List<AuthorityDTO> getAuthorityList() {
        return authorityRepository.findAll().stream().map(AuthorityDTO::new).collect(Collectors.toList());
    }
}
