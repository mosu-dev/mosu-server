//package life.mosu.mosuserver.application.applicationschool;
//
//import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
//import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
//import life.mosu.mosuserver.domain.refund.RefundJpaRepository;
//import life.mosu.mosuserver.domain.subject.Subject;
//import life.mosu.mosuserver.domain.subject.SubjectJpaEntity;
//import life.mosu.mosuserver.domain.subject.SubjectJpaRepository;
//import life.mosu.mosuserver.global.exception.CustomRuntimeException;
//import life.mosu.mosuserver.global.exception.ErrorCode;
//import life.mosu.mosuserver.persistence.application.ApplicationJpaRepository;
//import life.mosu.mosuserver.persistence.application.ApplicationSchoolJpaRepository;
//import life.mosu.mosuserver.persistence.profile.ProfileJpaRepository;
//import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
//import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;
//import life.mosu.mosuserver.presentation.applicationschool.dto.RefundRequest;
//import life.mosu.mosuserver.presentation.applicationschool.dto.SubjectUpdateRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ApplicationSchoolService {
//
//    private final ApplicationSchoolJpaRepository applicationSchoolJpaRepository;
//    private final ApplicationJpaRepository applicationJpaRepository;
//    private final RefundJpaRepository refundJpaRepository;
//    private final ProfileJpaRepository profileJpaRepository;
//    private final SubjectJpaRepository subjectJpaRepository;
//
//    @Transactional
//    public ApplicationSchoolResponse updateSubjects(
//        @RequestParam Long userId,
//        Long applicationSchoolId,
//        SubjectUpdateRequest request
//    ) {
//        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(applicationSchoolId)
//            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));
//
//        List<SubjectJpaEntity> subjects = subjectJpaRepository.findByApplicationSchoolId(applicationSchool.getId());
//        subjectJpaRepository.deleteAll();
//
//        for (Subject subject : request.subjects()) {
//            SubjectJpaEntity subjectEntity = SubjectJpaEntity.builder()
//                .applicationSchoolId(applicationSchoolId)
//                .subject(subject)
//                .build();
//            subjectJpaRepository.save(subject);
//        }
//        return null;
/// /        applicationSchool.updateSubjects(request.subjects());
/// /        return ApplicationSchoolResponse.from(applicationSchool);
//    }
//
//    @Transactional
//    public void cancelApplicationSchool(
//        Long userId,
//        Long applicationSchoolId,
//        RefundRequest request
//    ) {
//        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(applicationSchoolId)
//            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));
//
//        Long applicationId = applicationSchool.getApplicationId();
//        applicationSchoolJpaRepository.deleteById(applicationSchoolId);
//
//        if (!applicationSchoolJpaRepository.existsByApplicationId(applicationSchoolId)) {
//            applicationJpaRepository.deleteById(applicationId);
//        }
//
//        refundJpaRepository.save(request.toEntity(applicationId));
//    }
//
//    // 신청 내역 단건 조회
//    public ApplicationSchoolResponse getApplicationSchool(Long applicationSchoolId) {
//        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(applicationSchoolId)
//            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));
//
//        return ApplicationSchoolResponse.from(applicationSchool);
//    }
//
//    // 수험표 조회
//    public AdmissionTicketResponse getAdmissionTicket(Long userId, Long applicationSchoolId) {
//        Optional<ProfileJpaEntity> profile = Optional.ofNullable(profileJpaRepository.findById(userId)
//            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_NOT_FOUND)));
//
//
//    }
//
//}
