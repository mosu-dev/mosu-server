package life.mosu.mosuserver.domain.admin;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.QAdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.application.QApplicationJpaEntity;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.QApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.payment.QPaymentJpaEntity;
import life.mosu.mosuserver.domain.profile.QProfileJpaEntity;
import life.mosu.mosuserver.domain.school.QSchoolJpaEntity;
import life.mosu.mosuserver.domain.user.QUserJpaEntity;
import life.mosu.mosuserver.infra.property.S3Properties;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationFilter;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationListResponse;
import life.mosu.mosuserver.presentation.admin.dto.SchoolLunchResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicationQueryRepositoryImpl implements ApplicationQueryRepository {

    private static final DateTimeFormatter EXCEL_DT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final JPAQueryFactory queryFactory;
    private final S3Properties s3Properties;
    private final S3Service s3Service;

    private final QProfileJpaEntity profile = QProfileJpaEntity.profileJpaEntity;
    private final QApplicationJpaEntity application = QApplicationJpaEntity.applicationJpaEntity;
    private final QApplicationSchoolJpaEntity applicationSchool = QApplicationSchoolJpaEntity.applicationSchoolJpaEntity;
    private final QPaymentJpaEntity payment = QPaymentJpaEntity.paymentJpaEntity;
    private final QAdmissionTicketImageJpaEntity admissionTicketImage = QAdmissionTicketImageJpaEntity.admissionTicketImageJpaEntity;
    private final QUserJpaEntity user = QUserJpaEntity.userJpaEntity;
    private final QSchoolJpaEntity school = QSchoolJpaEntity.schoolJpaEntity;

    @Override
    public Page<ApplicationListResponse> searchAllApplications(ApplicationFilter filter,
            Pageable pageable) {

        JPAQuery<Tuple> query = baseQuery()
                .where(
                        buildNameCondition(filter.name()),
                        buildPhoneCondition(filter.phone())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<ApplicationListResponse> content = query.fetch().stream()
                .map(tuple -> {
                    Long appSchoolId = tuple.get(applicationSchool.id);
                    Set<Subject> subjects = appSchoolId != null
                            ? findSubjectsByApplicationSchoolId(appSchoolId)
                            : new HashSet<>();
                    return mapToResponse(tuple, subjects);
                })
                .toList();

        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public List<ApplicationExcelDto> searchAllApplicationsForExcel() {
        JPAQuery<Tuple> query = baseQuery();
        return query.fetch().stream()
                .map(tuple -> {
                    Long appSchoolId = tuple.get(applicationSchool.id);
                    Set<Subject> subjects = appSchoolId != null
                            ? findSubjectsByApplicationSchoolId(appSchoolId)
                            : new HashSet<>();
                    return mapToExcel(tuple, subjects);
                })
                .toList();
    }

    @Override
    public List<SchoolLunchResponse> searchAllSchoolLunches() {
        return queryFactory
                .select(
                        school.schoolName,
                        applicationSchool.lunch.count()
                )
                .from(applicationSchool)
                .rightJoin(school).on(applicationSchool.schoolId.eq(school.id))
                .where(applicationSchool.lunch.ne(Lunch.NONE))
                .groupBy(school.id, school.schoolName)
                .fetch()
                .stream()
                .map(t -> new SchoolLunchResponse(
                        t.get(school.schoolName),
                        t.get(applicationSchool.lunch.count())
                ))
                .toList();
    }


    private JPAQuery<Tuple> baseQuery() {
        return queryFactory
                .select(
                        applicationSchool.id,
                        payment.paymentKey,
                        applicationSchool.examinationNumber,
                        profile.userName,
                        profile.gender,
                        profile.birth,
                        profile.phoneNumber,
                        application.guardianPhoneNumber,
                        profile.education,
                        profile.schoolInfo.schoolName,
                        profile.grade,
                        applicationSchool.lunch,
                        applicationSchool.schoolName,
                        applicationSchool.examDate,
                        admissionTicketImage.s3Key,
                        admissionTicketImage.fileName,
                        payment.paymentStatus,
                        payment.paymentMethod,
                        application.createdAt
                )
                .from(applicationSchool)
                .leftJoin(application).on(applicationSchool.applicationId.eq(application.id))
                .leftJoin(payment).on(payment.applicationId.eq(application.id))
                .leftJoin(user).on(application.userId.eq(user.id))
                .leftJoin(profile).on(profile.userId.eq(user.id))
                .leftJoin(admissionTicketImage)
                .on(admissionTicketImage.applicationId.eq(application.id));
    }

    private Predicate buildNameCondition(String name) {
        return (name == null || name.isBlank())
                ? null
                : profile.userName.contains(name);
    }

    private Predicate buildPhoneCondition(String phone) {
        return (phone == null || phone.isBlank())
                ? null
                : profile.phoneNumber.contains(phone);
    }

    private Set<Subject> findSubjectsByApplicationSchoolId(Long applicationSchoolId) {
        EnumPath<Subject> subject = Expressions.enumPath(Subject.class, "subject");
        return new HashSet<>(
                queryFactory
                        .select(subject)
                        .from(applicationSchool)
                        .join(applicationSchool.subjects, subject)
                        .where(applicationSchool.id.eq(applicationSchoolId))
                        .fetch()
        );
    }

    private ApplicationListResponse mapToResponse(Tuple tuple, Set<Subject> subjects) {
        Set<String> subjectNames = subjects.stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toSet());

        String lunchName = tuple.get(applicationSchool.lunch).getLunchName();

        String s3Key = tuple.get(admissionTicketImage.s3Key);
        String url = getAdmissionTicketImageUrl(s3Key);

        AdmissionTicketResponse admissionTicket = AdmissionTicketResponse.of(
                url,
                tuple.get(profile.userName),
                tuple.get(profile.birth),
                tuple.get(applicationSchool.examinationNumber),
                subjectNames,
                tuple.get(applicationSchool.schoolName)
        );

        return new ApplicationListResponse(
                tuple.get(payment.paymentKey),
                tuple.get(applicationSchool.examinationNumber),
                tuple.get(profile.userName),
                tuple.get(profile.gender),
                tuple.get(profile.birth),
                tuple.get(profile.phoneNumber),
                tuple.get(application.guardianPhoneNumber),
                tuple.get(profile.education),
                tuple.get(profile.schoolInfo.schoolName),
                tuple.get(profile.grade),
                lunchName,
                subjectNames,
                tuple.get(applicationSchool.schoolName),
                tuple.get(applicationSchool.examDate),
                tuple.get(admissionTicketImage.fileName),
                tuple.get(payment.paymentStatus),
                tuple.get(payment.paymentMethod),
                tuple.get(application.createdAt),
                admissionTicket
        );
    }

    private ApplicationExcelDto mapToExcel(Tuple tuple, Set<Subject> subjects) {
        Set<String> subjectNames = subjects.stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toSet());

        String lunchName = tuple.get(applicationSchool.lunch).getLunchName();
        String genderName = tuple.get(profile.gender).getGenderName();
        String gradeName = tuple.get(profile.grade).getGradeName();
        String educationName = tuple.get(profile.education).getEducationName();
        String appliedAt = tuple.get(application.createdAt)
                .format(EXCEL_DT_FORMATTER);

        return new ApplicationExcelDto(
                tuple.get(payment.paymentKey),
                tuple.get(applicationSchool.examinationNumber),
                tuple.get(profile.userName),
                genderName,
                tuple.get(profile.birth),
                tuple.get(profile.phoneNumber),
                tuple.get(application.guardianPhoneNumber),
                educationName,
                tuple.get(profile.schoolInfo.schoolName),
                gradeName,
                lunchName,
                subjectNames,
                tuple.get(applicationSchool.schoolName),
                tuple.get(applicationSchool.examDate),
                tuple.get(admissionTicketImage.fileName),
                tuple.get(payment.paymentStatus),
                tuple.get(payment.paymentMethod),
                appliedAt
        );
    }

    private String getAdmissionTicketImageUrl(String s3Key) {
        if (s3Key == null || s3Key.isBlank()) {
            return null;
        }
        return s3Service.getPreSignedUrl(
                s3Key,
                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
        );
    }
}
