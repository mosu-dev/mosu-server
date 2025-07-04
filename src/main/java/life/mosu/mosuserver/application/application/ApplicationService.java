package life.mosu.mosuserver.application.application;

import jakarta.transaction.Transactional;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaRepository;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.subject.Subject;
import life.mosu.mosuserver.domain.subject.SubjectJpaEntity;
import life.mosu.mosuserver.domain.subject.SubjectJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.persistence.application.ApplicationJpaRepository;
import life.mosu.mosuserver.persistence.application.ApplicationSchoolJpaRepository;
import life.mosu.mosuserver.presentation.application.dto.ApplicationRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationResponse;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationSchoolJpaRepository applicationSchoolJpaRepository;
    private final AdmissionTicketImageJpaRepository admissionTicketImageJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;


    // 신청
    @Transactional
    public ApplicationResponse apply(Long userId, ApplicationRequest request) {

        Set<Long> schoolIds = request.schools().stream()
            .map(ApplicationSchoolRequest::schoolId)
            .collect(Collectors.toSet());

        if (applicationSchoolJpaRepository.existsByUserIdAndSchoolIdIn(userId, schoolIds)) {
            throw new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_ALREADY_APPLIED);
        }

        ApplicationJpaEntity application = ApplicationJpaEntity.builder()
            .userId(userId)
            .recommenderPhoneNumber(request.recommenderPhoneNumber())
            .agreedToNotices(request.agreementRequest().agreedToNotices())
            .agreedToNoticesAt(LocalDate.now())
            .agreedToRefundPolicy(request.agreementRequest().agreedToRefundPolicy())
            .agreedToRefundPolicyAt(LocalDate.now())
            .build();
        ApplicationJpaEntity applicationJpaEntity = applicationJpaRepository.save(application);
        Long applicationId = applicationJpaEntity.getId();
        admissionTicketImageJpaRepository.save(request.admissionTicket().toAdmissionTicketImageEntity(request.admissionTicket().fileName(), request.admissionTicket().s3Key(), applicationId));

        for (ApplicationSchoolRequest applicationSchoolRequest : request.schools()) {
            ApplicationSchoolJpaEntity applicationSchoolJpaEntity = applicationSchoolRequest.toEntity(applicationId);
//            applicationSchoolJpaEntity.generateExaminationNumber(); //수험 번호 수정 필요
            ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.save(applicationSchoolJpaEntity);

            for (Subject subject : applicationSchoolRequest.subjects()) {
                SubjectJpaEntity entity = SubjectJpaEntity.builder()
                    .applicationSchoolId(applicationSchool.getId())
                    .subject(subject)
                    .build();
                subjectJpaRepository.save(entity);
            }
        }

        List<ApplicationSchoolJpaEntity> schools = applicationSchoolJpaRepository.findAllByApplicationId(applicationId);

        //return ApplicationResponse.of(applicationId, schools, request.amount());
        return ApplicationResponse.of(applicationId, null, request.amount());
    }


//    // 전체 신청 내역 조회
//    public List<ApplicationResponse> getApplications(Long userId) {
//        Optional<ApplicationJpaEntity> applications = Optional.ofNullable(applicationJpaRepository.findAllByUserId(userId)
//            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.APPLICATION_LIST_NOT_FOUND)));
//
//        return applications.stream()
//            .map(application -> {
//                List<ApplicationSchoolJpaEntity> schools = applicationSchoolJpaRepository.findAllByApplicationId(application.getId());
//                return ApplicationResponse.of(application.getId(), schools, application.getAmount());
//            })
//            .toList();
//    }

    // 전체 신청 내역 조회
    public List<ApplicationResponse> getApplications(Long userId) {
        List<ApplicationJpaEntity> applications = applicationJpaRepository.findAll();
        List<ApplicationResponse> responses = new ArrayList<>();

        for (ApplicationJpaEntity application : applications) {
            // 해당 신청(application)에 연관된 학교들 조회
            List<ApplicationSchoolJpaEntity> schools =
                applicationSchoolJpaRepository.findAllByApplicationId(application.getId());

            List<ApplicationSchoolResponse> ar = new ArrayList<>();

            // 각 학교별로 신청된 과목들 조회
            for (ApplicationSchoolJpaEntity school : schools) {
                List<SubjectJpaEntity> subjectEntities =
                    subjectJpaRepository.findAllByApplicationSchoolId(school.getId());

                Set<Subject> subjects = new HashSet<>();
                for (SubjectJpaEntity subjectEntity : subjectEntities) {
                    subjects.add(subjectEntity.getSubject());
                }

                // 학교 + 해당 과목들 묶어서 response 생성
                ar.add(ApplicationSchoolResponse.of(school, subjects));
            }

            // ApplicationResponse 생성 후 리스트에 추가
            ApplicationResponse response = ApplicationResponse.of(
                application.getId(),
                ar,
                application.getAmount()
            );
            responses.add(response);
        }

        return responses;
    }


//    private void createAdmissionTicketImageIfPresent(FileRequest fileRequest, Long applicdationId) {
//        if (fileRequest == null) {
//            return;
//        }
//
//        fileUploadHelper.updateTag(fileRequest.s3Key());
//        admissionTicketJpaRepository.save(fileRequest.toAttachmentEntity(
//            fileRequest.fileName(),
//            fileRequest.s3Key(),
//            applicdationId
//        ));
//    }


}