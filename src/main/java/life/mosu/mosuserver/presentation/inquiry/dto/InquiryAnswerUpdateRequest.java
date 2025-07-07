package life.mosu.mosuserver.presentation.inquiry.dto;

import java.util.List;
import life.mosu.mosuserver.global.util.FileRequest;

public record InquiryAnswerUpdateRequest(
        String title,
        String content,
        List<FileRequest> attachments
) {

}
