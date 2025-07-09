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

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "customer_key")
    private String customerKey;

    @Column(name = "agreed_to_terms_of_service")
    private boolean agreedToTermsOfService;

    @Column(name = "agreed_to_privacy_policy")
    private boolean agreedToPrivacyPolicy;

    @Column(name = "agreed_to_marketing")
    private boolean agreedToMarketing;

    @Column(name = "user_role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    public UserJpaEntity(String loginId, String password, Gender gender, String name,
            LocalDate birth,
            String customerKey, boolean agreedToTermsOfService, boolean agreedToPrivacyPolicy,
            boolean agreedToMarketing, UserRole userRole) {
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.birth = birth;
        this.customerKey = customerKey;
        this.agreedToTermsOfService = agreedToTermsOfService;
        this.agreedToPrivacyPolicy = agreedToPrivacyPolicy;
        this.agreedToMarketing = agreedToMarketing;
        this.userRole = userRole;
    }
}
