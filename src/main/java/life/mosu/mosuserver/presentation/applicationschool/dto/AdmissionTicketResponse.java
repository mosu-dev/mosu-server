package life.mosu.mosuserver.presentation.applicationschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;

@Schema(description = "수험표 응답 DTO")
public record AdmissionTicketResponse(

        @Schema(description = "수험표 이미지 URL", example = "https://s3.amazonaws.com/bucket/admission/2025-00001.jpg")
        String admissionTicketImageUrl,

        @Schema(description = "응시자 이름", example = "홍길동")
        String userName,

        @Schema(description = "생년월일", example = "2005-05-10")
        LocalDate birth,

        @Schema(description = "수험 번호", example = "20250001")
        String examinationNumber,

        @Schema(description = "응시 과목 목록", example = "[\"생명과학\", \"지구과학\"]")
        Set<String> subjects,

        @Schema(description = "응시 학교명", example = "대치중학교")
        String schoolName

) {

    public static AdmissionTicketResponse of(
            String admissionTicketImageUrl,
            String userName,
            LocalDate birth,
            String examinationNumber,
            Set<String> subjects,
            String schoolName
    ) {
        return new AdmissionTicketResponse(
                admissionTicketImageUrl,
                userName,
                birth,
                examinationNumber,
                subjects,
                schoolName
        );
    }
}
