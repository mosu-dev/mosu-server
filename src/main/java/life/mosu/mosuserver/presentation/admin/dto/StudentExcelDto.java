package life.mosu.mosuserver.presentation.admin.dto;

import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.global.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentExcelDto {

    @ExcelColumn(headerName = "이름")
    private final String name;

    @ExcelColumn(headerName = "생년월일")
    private final String birthDate;

    @ExcelColumn(headerName = "전화번호")
    private final String phoneNumber;

    @ExcelColumn(headerName = "성별")
    private final String gender;

    @ExcelColumn(headerName = "학력")
    private final Education educationLevel;

    @ExcelColumn(headerName = "학교명")
    private final String schoolName;

    @ExcelColumn(headerName = "학년")
    private final Grade grade;

    @ExcelColumn(headerName = "시험 응시 횟수")
    private final int examCount;

}

