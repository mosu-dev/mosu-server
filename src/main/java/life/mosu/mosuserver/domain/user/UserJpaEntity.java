package life.mosu.mosuserver.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import life.mosu.mosuserver.domain.profile.Gender;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "name")
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "customer_key")
    private String customerKey;

    @Column(name = "user_role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    public UserJpaEntity(String loginId, String password, Gender gender, String name,
            LocalDate birth,
            UserRole userRole) {
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
        this.userRole = userRole;
    }
}
