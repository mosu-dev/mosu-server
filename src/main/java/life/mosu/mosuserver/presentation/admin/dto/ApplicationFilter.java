package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;

@Schema(description = "신청 목록 필터 DTO")
public record ApplicationFilter(

        @Schema(description = "이름 필터", example = "홍길동")
        String name,

        @Schema(description = "전화번호 필터", example = "01012345678")
        @PhoneNumberPattern
        String phone,

        @Schema(description = "신청 일자 필터", example = "2025-07-10")
        LocalDate applicationDate

) {

}
