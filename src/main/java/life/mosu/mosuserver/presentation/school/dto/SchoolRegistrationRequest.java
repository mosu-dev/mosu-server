package life.mosu.mosuserver.presentation.school.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.presentation.application.dto.AddressRequest;


@Schema(description = "학교 생성/등록 요청 DTO")
public record SchoolRegistrationRequest(
        @Schema(description = "학교 이름", example = "모수고등학교")
        @NotBlank(message = "학교 이름은 필수 입니다.")
        String schoolName,

        @Schema(description = "지역 (예: DAECHI, MOKDONG, NOWON)", example = "DAECHI")
        @NotNull(message = "지역은 필수 입니다.")
        String area,

        @Schema(description = "학교 주소 정보")
        @NotNull(message = "주소 정보는 필수 입니다.")
        AddressRequest address,

        @Schema(description = "시험/입학 시험 날짜 (YYYY-MM-DD)", example = "2025-11-20")
        @NotNull(message = "시험 날짜는 필수입니다.")
        LocalDate examDate,

        @Schema(description = "수용 인원", example = "300")
        @NotNull(message = "수용 인원은 필수입니다.")
        @Positive(message = "수용 인원은 양수여야 합니다.")
        Long capacity
) {

    public SchoolJpaEntity toEntity() {
        AddressJpaVO address = address().toValueObject();
        return SchoolJpaEntity.builder()
                .schoolName(schoolName)
                .area(Area.valueOf(area))
                .address(address)
                .examDate(examDate)
                .capacity(capacity)
                .build();
    }

}