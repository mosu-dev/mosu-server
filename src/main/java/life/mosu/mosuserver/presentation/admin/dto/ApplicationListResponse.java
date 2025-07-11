package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.PaymentStatus;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;

@Schema(description = "관리자 신청 목록 응답 DTO")
public record ApplicationListResponse(

        @Schema(description = "결제 번호", example = "P3135455")
        String paymentNumber,

        @Schema(description = "수험 번호", example = "20250001")
        String examinationNumber,

        @Schema(description = "수험자 이름", example = "홍길동")
        String name,

        @Schema(description = "성별", example = "남성")
        String gender,

        @Schema(description = "생년월일", example = "2005-05-10")
        LocalDate birth,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "보호자 전화번호", example = "010-9876-5432")
        String guardianPhoneNumber,

        @Schema(description = "학력 (예: 재학생, 졸업생)", example = "재학생")
        String educationLevel,

        @Schema(description = "학교명", example = "대치중학교")
        String schoolName,

        @Schema(description = "학년", example = "고등학교 1학년")
        String grade,

        @Schema(description = "도시락 신청 여부", example = "NONE")
        String lunch,

        @Schema(description = "응시 과목 목록", example = "[\"생활과 윤리\", \"정치와 법\"]")
        Set<String> subjects,

        @Schema(description = "시험 학교 이름", example = "서울고등학교")
        String examSchoolName,

        @Schema(description = "시험 일자", example = "2025-08-10")
        LocalDate examDate,

        @Schema(description = "수험표 이미지 URL", example = "https://s3.amazonaws.com/bucket/admission/2025-00001.jpg")
        String admissionTicketImage,

        @Schema(description = "결제 상태", example = "DONE")
        PaymentStatus paymentStatus,

        @Schema(description = "결제 방법", example = "CARD")
        PaymentMethod paymentMethod,

        @Schema(description = "신청 일시", example = "2025-07-10T15:30:00")
        LocalDateTime applicationDate,

        @Schema(description = "수험표 응답 정보")
        AdmissionTicketResponse admissionTicket

) {

}
