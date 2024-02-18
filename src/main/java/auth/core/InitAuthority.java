package auth.core;

import auth.entity.Authority;
import auth.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitAuthority {

    private AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {

        Authority authority1 = new Authority(1L, "최고관리자");
        Authority authority2 = new Authority(2L, "일반관리자");
        Authority authority3 = new Authority(3L, "일반사용자");

        authorityRepository.save(authority1);
        authorityRepository.save(authority2);
        authorityRepository.save(authority3);

    }

}
