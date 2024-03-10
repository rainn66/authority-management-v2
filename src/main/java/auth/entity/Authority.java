package auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends CommonBase{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authorityCd;
    private String authorityNm;
}
