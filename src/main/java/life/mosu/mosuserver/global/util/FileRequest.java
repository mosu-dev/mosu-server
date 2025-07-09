package life.mosu.mosuserver.global.util;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerAttachmentEntity;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentJpaEntity;
import life.mosu.mosuserver.infra.storage.domain.Visibility;

public record FileRequest(
        @Schema(description = "파일 이름", example = "example.jpg")
        String fileName,
        @Schema(description = "S3 키", example = "비공개 이미지를 처리하기 위한 키")
        String s3Key
) {

    public FaqAttachmentJpaEntity toFaqAttachmentEntity(String fileName, String s3Key, Long faqId) {
        return FaqAttachmentJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PUBLIC)
                .faqId(faqId)
                .build();
    }

    public NoticeAttachmentJpaEntity toNoticeAttachmentEntity(String fileName, String s3Key,
            Long noticeId) {
        return NoticeAttachmentJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PUBLIC)
                .noticeId(noticeId)
                .build();
    }

    public InquiryAttachmentJpaEntity toInquiryAttachmentEntity(String fileName, String s3Key,
            Long inquiryId) {
        return InquiryAttachmentJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PRIVATE)
                .inquiryId(inquiryId)
                .build();
    }


    public AdmissionTicketImageJpaEntity toAdmissionTicketImageEntity(String fileName, String s3Key,
            Long applicationId) {
        return AdmissionTicketImageJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PRIVATE)
                .applicationId(applicationId)
                .build();
    }

    public InquiryAnswerAttachmentEntity toInquiryAnswerAttachmentEntity(String fileName,
            String s3Key,
            Long inquiryAnswerId) {
        return InquiryAnswerAttachmentEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PRIVATE)
                .inquiryAnswerId(inquiryAnswerId)
                .build();
    }
}
