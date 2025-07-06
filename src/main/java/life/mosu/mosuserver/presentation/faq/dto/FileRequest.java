package life.mosu.mosuserver.presentation.faq.dto;

import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.infra.storage.domain.Visibility;

public record FileRequest(
        String fileName,
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
}
