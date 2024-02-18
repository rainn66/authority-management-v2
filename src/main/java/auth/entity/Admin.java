package auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private Long adminIdx;

    private String adminId;
    private String adminNm;
    private String password;
    private LocalDateTime lastLoginDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_idx")
    private Authority authority;

}
