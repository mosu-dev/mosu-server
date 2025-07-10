package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.global.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "학생 엑셀 다운로드 DTO")
@AllArgsConstructor
@Getter
public class StudentExcelDto {

    @Schema(description = "이름", example = "홍길동")
    @ExcelColumn(headerName = "이름")
    private final String name;

    @Schema(description = "생년월일 (yyyy-MM-dd 형식)", example = "2006-04-12")
    @ExcelColumn(headerName = "생년월일")
    private final String birthDate;

    @Schema(description = "전화번호", example = "01012345678")
    @ExcelColumn(headerName = "전화번호")
    private final String phoneNumber;

    @Schema(description = "성별", example = "남자")
    @ExcelColumn(headerName = "성별")
    private final String gender;

    @Schema(description = "학력", example = "HIGH_SCHOOL")
    @ExcelColumn(headerName = "학력")
    private final String educationLevel;

    @Schema(description = "학교명", example = "서울고등학교")
    @ExcelColumn(headerName = "학교명")
    private final String schoolName;

    @Schema(description = "학년", example = "THIRD")
    @ExcelColumn(headerName = "학년")
    private final String grade;

    @Schema(description = "시험 응시 횟수", example = "2")
    @ExcelColumn(headerName = "시험 응시 횟수")
    private final int examCount;

}
