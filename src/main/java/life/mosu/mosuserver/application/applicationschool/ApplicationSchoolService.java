package life.mosu.mosuserver.application.applicationschool;

import java.time.Duration;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaRepository;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.domain.application.ApplicationJpaRepository;
import life.mosu.mosuserver.domain.application.ApplicationSchoolJpaRepository;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaRepository;
import life.mosu.mosuserver.domain.refund.RefundJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.RefundRequest;
import life.mosu.mosuserver.presentation.applicationschool.dto.SubjectUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationSchoolService {

    private final ApplicationSchoolJpaRepository applicationSchoolJpaRepository;
    private final ApplicationJpaRepository applicationJpaRepository;
    private final RefundJpaRepository refundJpaRepository;
    private final ProfileJpaRepository profileJpaRepository;
    private final AdmissionTicketImageJpaRepository admissionTicketImageJpaRepository;
    private final S3Service s3Service;

    @Value("${aws.s3.presigned-url-expiration-minutes}")
    private int durationTime;

    @Transactional
    public ApplicationSchoolResponse updateSubjects(
            @RequestParam Long userId,
            Long applicationSchoolId,
            SubjectUpdateRequest request
    ) {
        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(
                        applicationSchoolId)
                .orElseThrow(
                        () -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));

        applicationSchool.updateSubjects(request.subjects());
        return ApplicationSchoolResponse.from(applicationSchool);
    }

    @Transactional
    public void cancelApplicationSchool(
            @RequestParam Long userId,
            Long applicationSchoolId,
            RefundRequest request
    ) {
        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(
                        applicationSchoolId)
                .orElseThrow(
                        () -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));

        Long applicationId = applicationSchool.getApplicationId();
        applicationSchoolJpaRepository.deleteById(applicationSchoolId);

        if (!applicationSchoolJpaRepository.existsByApplicationId(applicationId)) {
            applicationJpaRepository.deleteById(applicationId);
        }

        refundJpaRepository.save(request.toEntity(applicationSchoolId));
    }

    // 신청 내역 단건 조회
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ApplicationSchoolResponse getApplicationSchool(Long applicationSchoolId) {
        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(
                        applicationSchoolId)
                .orElseThrow(
                        () -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));

        return ApplicationSchoolResponse.from(applicationSchool);
    }

    // 수험표 조회
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AdmissionTicketResponse getAdmissionTicket(Long userId, Long applicationSchoolId) {
        ProfileJpaEntity profile = profileJpaRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_NOT_FOUND));

        ApplicationSchoolJpaEntity applicationSchool = applicationSchoolJpaRepository.findById(
                        applicationSchoolId)
                .orElseThrow(
                        () -> new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_NOT_FOUND));

        ApplicationJpaEntity application = applicationJpaRepository.findById(
                        applicationSchool.getApplicationId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.APPLICATION_NOT_FOUND));

        AdmissionTicketImageJpaEntity admissionTicketImage = admissionTicketImageJpaRepository.findByApplicationId(
                application.getId());

        return AdmissionTicketResponse.of(
                getAdmissionTicketImageUrl(admissionTicketImage),
                profile.getUserName(),
                profile.getBirth(),
                applicationSchool.getExaminationNumber(),
                applicationSchool.getSubjects(),
                applicationSchool.getSchoolName()
        );

    }

    private String getAdmissionTicketImageUrl(AdmissionTicketImageJpaEntity admissionTicketImage) {
        return s3Service.getPreSignedUrl(
                admissionTicketImage.getS3Key(),
                Duration.ofMinutes(durationTime)
        );
    }

}
