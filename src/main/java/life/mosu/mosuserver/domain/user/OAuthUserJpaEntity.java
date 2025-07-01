package life.mosu.mosuserver.domain.user;

import jakarta.persistence.*;
import life.mosu.mosuserver.applicaiton.oauth.OAuthUserInfo;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthUserJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public OAuthUserJpaEntity(
        final String email,
        final String name,
        final UserRole userRole
    ) {
        this.email = email;
        this.name = name;
        this.userRole = userRole;
    }

    public void updateInfo(final OAuthUserInfo info) {
        this.email = info.email();
        this.name = info.name();
        this.userRole = UserRole.ROLE_USER;
    }
}