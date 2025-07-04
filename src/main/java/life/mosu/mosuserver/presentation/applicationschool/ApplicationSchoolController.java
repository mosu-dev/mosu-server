//package life.mosu.mosuserver.presentation.applicationschool;
//
//import life.mosu.mosuserver.application.applicationschool.ApplicationSchoolService;
//import life.mosu.mosuserver.global.util.ApiResponseWrapper;
//import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
//import life.mosu.mosuserver.presentation.applicationschool.dto.RefundRequest;
//import life.mosu.mosuserver.presentation.applicationschool.dto.SubjectUpdateRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/application/school")
//@RequiredArgsConstructor
//public class ApplicationSchoolController {
//
//    private final ApplicationSchoolService applicationSchoolService;
//
//    @PostMapping("/{applicationSchoolId}/cancel")
//    public ApiResponseWrapper<Void> cancelApplicationSchool(
//        @PathVariable("applicationSchoolId") Long applicationSchoolId,
//        @RequestParam Long userId,
//        @RequestBody RefundRequest request
//    ) {
//        applicationSchoolService.cancelApplicationSchool(userId, applicationSchoolId, request);
//        return ApiResponseWrapper.success(HttpStatus.OK, "신청 학교 취소 및 환불 처리 완료");
//    }
//
//    @PutMapping("/{applicationSchoolId}/subjects")
//    public ApiResponseWrapper<ApplicationSchoolResponse> updateSubjects(
//        @PathVariable("applicationSchoolId") Long applicationSchoolId,
//        @RequestParam Long userId,
//        @RequestBody SubjectUpdateRequest request
//    ) {
//        ApplicationSchoolResponse response = applicationSchoolService.updateSubjects(userId, applicationSchoolId, request);
//        return ApiResponseWrapper.success(HttpStatus.OK, "신청 과목 수정 성공", response);
//    }
//
//    @GetMapping("/{applicationSchoolId}")
//    public ApiResponseWrapper<ApplicationSchoolResponse> getDetail(
//        @RequestParam Long userId,
//        @PathVariable("applicationSchoolId") Long applicationSchoolId
//    ) {
//        ApplicationSchoolResponse response = applicationSchoolService.getApplicationSchool(applicationSchoolId);
//        return ApiResponseWrapper.success(HttpStatus.OK, "신청 상세 조회 성공", response);
//    }
//}