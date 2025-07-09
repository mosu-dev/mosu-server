package life.mosu.mosuserver.presentation.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.PaymentStatus;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;

public record ApplicationListResponse(
        String paymentNumber,
        String examinationNumber,
        String name,
        Gender gender,
        LocalDate birth,
        String phoneNumber,
        String guardianPhoneNumber,
        Education educationLevel,
        String schoolName,
        Grade grade,
        String lunch,
        Set<String> subjects,
        String examSchoolName,
        LocalDate examDate,
        String admissionTicketImage,
        PaymentStatus paymentStatus,
        PaymentMethod paymentMethod,
        LocalDateTime applicationDate,
        AdmissionTicketResponse admissionTicket
) {

}
