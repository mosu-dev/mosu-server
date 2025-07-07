package life.mosu.mosuserver.presentation.inquiry.dto;

import java.util.List;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;

public record InquiryAnswerUpdateRequest(
        String title,
        String content,
        List<FileRequest> attachments
) {

}
