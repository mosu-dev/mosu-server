package life.mosu.mosuserver.presentation.admin.dto;

import java.time.LocalDate;
import java.util.Set;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.PaymentStatus;
import life.mosu.mosuserver.global.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationExcelDto {

    @ExcelColumn(headerName = "결제 번호")
    private final String paymentNumber;

    @ExcelColumn(headerName = "수험 번호")
    private final String examinationNumber;

    @ExcelColumn(headerName = "이름")
    private final String name;

    @ExcelColumn(headerName = "성별")
    private final String gender;

    @ExcelColumn(headerName = "생년월일")
    private final LocalDate birth;

    @ExcelColumn(headerName = "전화번호")
    private final String phoneNumber;

    @ExcelColumn(headerName = "보호자 전화번호")
    private final String guardianPhoneNumber;

    @ExcelColumn(headerName = "학력")
    private final String educationLevel;

    @ExcelColumn(headerName = "학교명")
    private final String schoolName;

    @ExcelColumn(headerName = "학년")
    private final String grade;

    @ExcelColumn(headerName = "도시락")
    private final String lunch;

    @ExcelColumn(headerName = "응시 과목")
    private final Set<String> subjects;

    @ExcelColumn(headerName = "시험 학교")
    private final String examSchoolName;

    @ExcelColumn(headerName = "시험 일자")
    private final LocalDate examDate;

    @ExcelColumn(headerName = "수험표 사진")
    private final String admissionTicketImage;

    @ExcelColumn(headerName = "결제 상태")
    private final PaymentStatus paymentStatus;

    @ExcelColumn(headerName = "결제 방법")
    private final PaymentMethod paymentMethod;

    @ExcelColumn(headerName = "신청 일시")
    private final String applicationDate;
}
