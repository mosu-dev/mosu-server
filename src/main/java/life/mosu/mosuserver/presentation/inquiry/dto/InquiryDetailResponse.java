package life.mosu.mosuserver.presentation.inquiry.dto;

import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;

public record InquiryDetailResponse(
        Long id,
        String title,
        String content,
        String author,
        InquiryStatus status,
        String createdAt,
        List<AttachmentResponse> attachments,
        InquiryAnswerDetailResponse answer
) {

    public static InquiryDetailResponse of(
            InquiryJpaEntity inquiry,
            List<AttachmentResponse> attachments,
            InquiryAnswerDetailResponse answer
    ) {
        return new InquiryDetailResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getContent(),
                inquiry.getAuthor(),
                inquiry.getStatus(),
                inquiry.getCreatedAt(),
                attachments,
                answer
        );
    }

    public record AttachmentResponse(String fileName, String url) {

    }

    public record InquiryAnswerDetailResponse(
            Long id,
            String title,
            String content,
            String createdAt,
            List<AttachmentResponse> attachments
    ) {

        public static InquiryAnswerDetailResponse of(InquiryAnswerJpaEntity answer,
                List<AttachmentResponse> attachments) {
            return new InquiryAnswerDetailResponse(
                    answer.getId(),
                    answer.getTitle(),
                    answer.getContent(),
                    answer.getCreatedAt(),
                    attachments
            );
        }

    }


}
