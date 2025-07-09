package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.PaymentStatus;
import life.mosu.mosuserver.global.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "신청 엑셀 데이터 DTO")
@AllArgsConstructor
@Getter
public class ApplicationExcelDto {

    @Schema(description = "결제 번호", example = "PAY-20250710-0001")
    @ExcelColumn(headerName = "결제 번호")
    private final String paymentNumber;

    @Schema(description = "수험 번호", example = "2025-00001")
    @ExcelColumn(headerName = "수험 번호")
    private final String examinationNumber;

    @Schema(description = "수험자 이름", example = "홍길동")
    @ExcelColumn(headerName = "이름")
    private final String name;

    @Schema(description = "성별", example = "남자")
    @ExcelColumn(headerName = "성별")
    private final String gender;

    @Schema(description = "생년월일", example = "2005-05-10")
    @ExcelColumn(headerName = "생년월일")
    private final LocalDate birth;

    @Schema(description = "전화번호", example = "01012345678")
    @ExcelColumn(headerName = "전화번호")
    private final String phoneNumber;

    @Schema(description = "보호자 전화번호", example = "01098765432")
    @ExcelColumn(headerName = "보호자 전화번호")
    private final String guardianPhoneNumber;

    @Schema(description = "학력", example = "고등학교 재학")
    @ExcelColumn(headerName = "학력")
    private final String educationLevel;

    @Schema(description = "학교명", example = "서울고등학교")
    @ExcelColumn(headerName = "학교명")
    private final String schoolName;

    @Schema(description = "학년", example = "3학년")
    @ExcelColumn(headerName = "학년")
    private final String grade;

    @Schema(description = "도시락 신청 여부", example = "신청함")
    @ExcelColumn(headerName = "도시락")
    private final String lunch;

    @Schema(description = "응시 과목 목록", example = "[\"국어\", \"수학\"]")
    @ExcelColumn(headerName = "응시 과목")
    private final Set<String> subjects;

    @Schema(description = "시험 학교", example = "서울고등학교")
    @ExcelColumn(headerName = "시험 학교")
    private final String examSchoolName;

    @Schema(description = "시험 일자", example = "2025-08-10")
    @ExcelColumn(headerName = "시험 일자")
    private final LocalDate examDate;

    @Schema(description = "수험표 이미지 URL", example = "https://s3.amazonaws.com/bucket/admission/2025-00001.jpg")
    @ExcelColumn(headerName = "수험표 사진")
    private final String admissionTicketImage;

    @Schema(description = "결제 상태", example = "COMPLETED")
    @ExcelColumn(headerName = "결제 상태")
    private final PaymentStatus paymentStatus;

    @Schema(description = "결제 방법", example = "CARD")
    @ExcelColumn(headerName = "결제 방법")
    private final PaymentMethod paymentMethod;

    @Schema(description = "신청 일시", example = "2025-07-10 15:30:00")
    @ExcelColumn(headerName = "신청 일시")
    private final String applicationDate;
}
