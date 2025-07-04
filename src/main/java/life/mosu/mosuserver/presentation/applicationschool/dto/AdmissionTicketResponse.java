package life.mosu.mosuserver.presentation.applicationschool.dto;

import life.mosu.mosuserver.domain.subject.Subject;

import java.util.Set;

public record AdmissionTicketResponse(
    String userName,
    String birth,
    String examinationNumber,
    String admissionTicketImageUrl,
    Set<Subject> subjects,
    String schoolName
) {

//    public AdmissionTicketResponse getAdmissionTicket(Long userId, Long applicationSchoolId) {
//
//    }
}
