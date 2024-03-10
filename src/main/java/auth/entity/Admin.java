package auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends CommonBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminIdx;

    private String userId;
    private String userNm;
    private String password;
    private LocalDateTime lastLoginDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_cd")
    private Authority authority;

    public void update(String userNm, Authority authority) {
        this.userNm = userNm;
        this.authority = authority;
    }

    public void update(LocalDateTime lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

}
