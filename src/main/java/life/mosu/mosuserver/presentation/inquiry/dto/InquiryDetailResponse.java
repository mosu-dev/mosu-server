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
        List<AttachmentDetailResponse> attachments,
        InquiryAnswerDetailResponse answer
) {

    public static InquiryDetailResponse of(
            InquiryJpaEntity inquiry,
            List<AttachmentDetailResponse> attachments,
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

    public record AttachmentDetailResponse(String fileName, String url, String s3Key) {

    }

    public record InquiryAnswerDetailResponse(
            Long id,
            String title,
            String content,
            String createdAt,
            List<AttachmentDetailResponse> attachments
    ) {

        public static InquiryAnswerDetailResponse of(InquiryAnswerJpaEntity answer,
                List<AttachmentDetailResponse> attachments) {
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
