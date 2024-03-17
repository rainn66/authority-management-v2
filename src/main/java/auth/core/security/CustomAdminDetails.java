package auth.core.security;

import auth.core.constants.AuthorityConstants;
import auth.entity.Admin;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class CustomAdminDetails implements UserDetails {

    private Admin admin;

    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
    }

    public String getUserNm() {
        return admin.getUserNm();
    }

    public Collection<String> getAuthoritiesStrArr() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorityCdList = new ArrayList<>();
        String authorityCd = admin.getAuthority().getAuthorityCd();
        authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_001));
        if (AuthorityConstants.AUTH_002.equals(authorityCd)) {
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_002));
        } else if (AuthorityConstants.AUTH_003.equals(authorityCd)) {
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_002));
            authorityCdList.add(new SimpleGrantedAuthority(AuthorityConstants.AUTH_003));
        }
        return authorityCdList;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
