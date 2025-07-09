package life.mosu.mosuserver.application.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaRepository;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.domain.application.ApplicationJpaRepository;
import life.mosu.mosuserver.domain.application.ApplicationSchoolJpaRepository;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.domain.school.SchoolJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.global.util.FileRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationResponse;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationSchoolJpaRepository applicationSchoolJpaRepository;
    private final AdmissionTicketImageJpaRepository admissionTicketImageJpaRepository;
    private final SchoolJpaRepository schoolJpaRepository;

    // 신청
    @Transactional
    public ApplicationResponse apply(Long userId, ApplicationRequest request) {
        Set<ApplicationSchoolRequest> schools = request.schools();
        List<ApplicationSchoolJpaEntity> schoolEntities = new ArrayList<>();

        Set<Long> schoolIds = schools.stream()
                .map(ApplicationSchoolRequest::schoolId)
                .collect(Collectors.toSet());

        if (applicationSchoolJpaRepository.existsByUserIdAndSchoolIdIn(userId, schoolIds)) {
            throw new CustomRuntimeException(ErrorCode.APPLICATION_SCHOOL_ALREADY_APPLIED);
        }

        ApplicationJpaEntity application = request.toEntity(userId);
        ApplicationJpaEntity applicationJpaEntity = applicationJpaRepository.save(application);
        Long applicationId = applicationJpaEntity.getId();

        admissionTicketImageJpaRepository.save(
                createAdmissionTicketImageIfPresent(request.admissionTicket(), applicationId));

        schools.forEach(applicationSchoolRequest -> {
            SchoolJpaEntity school = schoolJpaRepository.findById(
                            applicationSchoolRequest.schoolId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.SCHOOL_NOT_FOUND));

            AddressJpaVO address = school.getAddress();
            ApplicationSchoolJpaEntity applicationSchoolJpaEntity = applicationSchoolRequest.toEntity(
                    userId, applicationId, address);
            ApplicationSchoolJpaEntity saved = applicationSchoolJpaRepository.save(
                    applicationSchoolJpaEntity);
            schoolEntities.add(saved);
        });

        return ApplicationResponse.of(applicationId, schoolEntities);
    }


    // 전체 신청 내역 조회
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ApplicationResponse> getApplications(Long userId) {
        List<ApplicationJpaEntity> applications = applicationJpaRepository.findAllByUserId(userId);

        return applications.stream()
                .map(application -> {
                    List<ApplicationSchoolJpaEntity> schools = applicationSchoolJpaRepository.findAllByApplicationId(
                            application.getId());
                    return ApplicationResponse.of(application.getId(), schools);
                })
                .toList();
    }

    private AdmissionTicketImageJpaEntity createAdmissionTicketImageIfPresent(
            FileRequest fileRequest, Long applicationId) {
        return admissionTicketImageJpaRepository.save(
                fileRequest.toAdmissionTicketImageEntity(fileRequest.fileName(),
                        fileRequest.s3Key(), applicationId));
    }

}