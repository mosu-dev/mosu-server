package life.mosu.mosuserver.presentation.applicationschool;

import life.mosu.mosuserver.application.applicationschool.ApplicationSchoolService;
import life.mosu.mosuserver.global.annotation.UserId;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.RefundRequest;
import life.mosu.mosuserver.presentation.applicationschool.dto.SubjectUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/applicationschool")
@RequiredArgsConstructor
public class ApplicationSchoolController implements ApplicationSchoolControllerDocs {

    private final ApplicationSchoolService applicationSchoolService;

    @DeleteMapping("/{applicationSchoolId}/cancel")
    public ResponseEntity<ApiResponseWrapper<Void>> cancelApplicationSchool(
            @PathVariable("applicationSchoolId") Long applicationSchoolId,
            @RequestParam Long userId,
            @RequestBody RefundRequest request
    ) {
        applicationSchoolService.cancelApplicationSchool(applicationSchoolId, request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "신청 학교 취소 및 환불 처리 완료"));
    }

    @PutMapping("/{applicationSchoolId}/subjects")
    public ResponseEntity<ApiResponseWrapper<ApplicationSchoolResponse>> updateSubjects(
            @PathVariable("applicationSchoolId") Long applicationSchoolId,
            @RequestParam Long userId,
            @RequestBody SubjectUpdateRequest request
    ) {
        ApplicationSchoolResponse response = applicationSchoolService.updateSubjects(
                applicationSchoolId, request);
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "신청 과목 수정 성공", response));
    }

    @GetMapping("/{applicationSchoolId}")
    public ResponseEntity<ApiResponseWrapper<ApplicationSchoolResponse>> getDetail(
            @UserId Long userId,
            @PathVariable("applicationSchoolId") Long applicationSchoolId
    ) {
        ApplicationSchoolResponse response = applicationSchoolService.getApplicationSchool(
                applicationSchoolId);
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "신청 상세 조회 성공", response));
    }

    @GetMapping("/{applicationSchoolId}/admissionticket")
    public ResponseEntity<ApiResponseWrapper<AdmissionTicketResponse>> getAdmissionTicket(
            @UserId Long userId,
            @PathVariable("applicationSchoolId") Long applicationSchoolId
    ) {
        AdmissionTicketResponse response = applicationSchoolService.getAdmissionTicket(userId,
                applicationSchoolId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "수험표 조회 성공", response));
    }
}