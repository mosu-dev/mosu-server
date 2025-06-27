package life.mosu.mosuserver.domain.profile;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Education education;

    @Embedded
    private SchoolInfoJpaVO schoolInfo;

    @Enumerated(EnumType.STRING)
    private Grade grade;

}
