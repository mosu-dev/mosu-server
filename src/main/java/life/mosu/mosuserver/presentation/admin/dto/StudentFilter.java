package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;

@Schema(description = "학생 목록 필터 DTO")
public record StudentFilter(

        @Schema(description = "이름 필터", example = "홍길동")
        String name,

        @Schema(description = "전화번호 필터", example = "01012345678")
        @PhoneNumberPattern
        String phone,

        @Schema(description = "정렬 순서 (desc 또는 asc)", example = "desc", defaultValue = "desc")
        String order

) {

    public StudentFilter {
        if (order == null || order.isBlank()) {
            order = "desc";
        }
    }
}
