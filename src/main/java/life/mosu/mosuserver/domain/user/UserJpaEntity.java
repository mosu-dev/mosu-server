package life.mosu.mosuserver.domain.user;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public UserJpaEntity(
        final String loginId,
        final String password,
        final UserRole userRole
    ) {
        this.loginId = loginId;
        this.password = password;
        this.userRole = userRole;
    }
}
