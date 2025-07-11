package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학생 목록 응답 DTO")
public record StudentListResponse(

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "생년월일 (yyyy-MM-dd 형식)", example = "2006-04-12")
        String birthDate,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "성별", example = "남자")
        String gender,

        @Schema(description = "학력", example = "ENROLLED")
        String educationLevel,

        @Schema(description = "학교명", example = "서울고등학교")
        String schoolName,

        @Schema(description = "학년", example = "고등학교 1학년")
        String grade,

        @Schema(description = "시험 응시 횟수", example = "2")
        int examCount

) {

}
