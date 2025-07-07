package life.mosu.mosuserver.application.inquiry;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryRepository;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryAnswerService {

    private final InquiryAnswerAttachmentService answerAttachmentService;
    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final InquiryRepository inquiryRepository;

    @Transactional
    public void createInquiryAnswer(Long postId, InquiryAnswerRequest request) {
        InquiryJpaEntity inquiryEntity = inquiryRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_NOT_FOUND));

        InquiryAnswerJpaEntity answerEntity = inquiryAnswerRepository.save(
                request.toEntity(postId));

        answerAttachmentService.createAttachment(request.attachments(), answerEntity);
        inquiryEntity.updateStatusToComplete();
    }

    @Transactional
    public void deleteInquiryAnswer(Long postId) {
        InquiryJpaEntity inquiryEntity = inquiryRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_NOT_FOUND));
        
        InquiryAnswerJpaEntity answerEntity = inquiryAnswerRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQURIY_ANSWER_NOT_FOUND));

        inquiryAnswerRepository.delete(answerEntity);
        answerAttachmentService.deleteAttachment(answerEntity);
        inquiryEntity.updateStatusToPending();
    }

}
