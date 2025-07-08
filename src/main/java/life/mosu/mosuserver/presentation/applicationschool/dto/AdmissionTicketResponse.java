package life.mosu.mosuserver.presentation.applicationschool.dto;

import java.time.LocalDate;
import java.util.Set;

public record AdmissionTicketResponse(
        String admissionTicketImageUrl,
        String userName,
        LocalDate birth,
        String examinationNumber,
        Set<String> subjects,
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
