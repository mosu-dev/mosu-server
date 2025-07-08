package life.mosu.mosuserver.global.util;

import life.mosu.mosuserver.domain.application.AdmissionTicketImageJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerAttachmentEntity;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentJpaEntity;
import life.mosu.mosuserver.infra.storage.domain.Visibility;

public record FileRequest(
        String fileName,
        String s3Key
) {

    /**
     * Creates a FaqAttachmentJpaEntity with the specified file name, S3 key, and FAQ ID, setting its visibility to public.
     *
     * @param fileName the name of the file to associate with the FAQ attachment
     * @param s3Key the S3 storage key for the file
     * @param faqId the identifier of the FAQ to which the attachment belongs
     * @return a new FaqAttachmentJpaEntity instance with public visibility
     */
    public FaqAttachmentJpaEntity toFaqAttachmentEntity(String fileName, String s3Key, Long faqId) {
        return FaqAttachmentJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PUBLIC)
                .faqId(faqId)
                .build();
    }

    /**
     * Creates a NoticeAttachmentJpaEntity with the specified file name, S3 key, and notice ID, setting its visibility to public.
     *
     * @param fileName the name of the file to associate with the notice attachment
     * @param s3Key the S3 storage key for the file
     * @param noticeId the identifier of the notice to which the attachment belongs
     * @return a NoticeAttachmentJpaEntity configured with the provided details and public visibility
     */
    public NoticeAttachmentJpaEntity toNoticeAttachmentEntity(String fileName, String s3Key,
            Long noticeId) {
        return NoticeAttachmentJpaEntity.builder()
                .fileName(fileName)
                .s3Key(s3Key)
                .visibility(Visibility.PUBLIC)
                .noticeId(noticeId)
                .build();
    }

    /**
     * Creates an InquiryAttachmentJpaEntity with the specified file name, S3 key, and inquiry ID, setting the visibility to private.
     *
     * @param fileName the name of the file to associate with the inquiry attachment
     * @param s3Key the S3 storage key for the file
     * @param inquiryId the ID of the inquiry to which the attachment belongs
     * @return a new InquiryAttachmentJpaEntity configured with the provided values and private visibility
     */
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
