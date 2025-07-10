package life.mosu.mosuserver.domain.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import life.mosu.mosuserver.presentation.profile.dto.EditProfileRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Education education;

    @Embedded
    private SchoolInfoJpaVO schoolInfo;

    @Column(name = "recommender_phone_number")
    private String recommenderPhoneNumber;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Builder
    public ProfileJpaEntity(
            final Long userId,
            final String userName,
            final Gender gender,
            final LocalDate birth,
            final String phoneNumber,
            final String email,
            final Education education,
            final SchoolInfoJpaVO schoolInfo,
            final Grade grade
    ) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.education = education;
        this.schoolInfo = schoolInfo;
        this.grade = grade;
    }

    public void edit(final EditProfileRequest request) {
        this.userName = request.userName();
        this.gender = request.validatedGender();
        this.birth = request.birth();
        this.phoneNumber = request.phoneNumber();
        this.email = request.email();
        this.education = request.education();
        this.schoolInfo = request.schoolInfo().toEntity();
        this.grade = request.grade();
    }

    public void registerRecommenderPhoneNumber(final String recommenderPhoneNumber) {
        this.recommenderPhoneNumber = recommenderPhoneNumber;
    }
}
