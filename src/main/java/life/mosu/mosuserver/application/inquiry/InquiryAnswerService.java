package life.mosu.mosuserver.application.inquiry;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaRepository;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryAnswerRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryAnswerUpdateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse.InquiryAnswerDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryAnswerService {

    private final InquiryAnswerAttachmentService answerAttachmentService;
    private final InquiryAnswerJpaRepository inquiryAnswerJpaRepository;
    private final InquiryJpaRepository inquiryJpaRepository;

    @Transactional
    public void createInquiryAnswer(Long postId, InquiryAnswerRequest request) {
        InquiryJpaEntity inquiryEntity = getInquiryOrThrow(postId);

        if (inquiryAnswerJpaRepository.findByInquiryId(postId).isPresent()) {
            throw new CustomRuntimeException(ErrorCode.INQUIRY_ANSWER_ALREADY_EXISTS);
        }

        InquiryAnswerJpaEntity answerEntity = inquiryAnswerJpaRepository.save(
                request.toEntity(postId));

        answerAttachmentService.createAttachment(request.attachments(), answerEntity);
        inquiryEntity.updateStatusToComplete();
    }

    @Transactional
    public void deleteInquiryAnswer(Long postId) {
        InquiryJpaEntity inquiryEntity = getInquiryOrThrow(postId);

        InquiryAnswerJpaEntity answerEntity = inquiryAnswerJpaRepository.findByInquiryId(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_ANSWER_NOT_FOUND));

        inquiryAnswerJpaRepository.delete(answerEntity);
        answerAttachmentService.deleteAttachment(answerEntity);
        inquiryEntity.updateStatusToPending();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public InquiryDetailResponse.InquiryAnswerDetailResponse getInquiryAnswerDetail(
            Long inquiryId) {
        return inquiryAnswerJpaRepository.findByInquiryId(inquiryId)
                .map(answer -> InquiryAnswerDetailResponse.of(
                        answer,
                        answerAttachmentService.toAttachmentResponses(answer)
                ))
                .orElse(null);
    }

    @Transactional
    public void updateInquiryAnswer(Long postId, InquiryAnswerUpdateRequest request) {
        InquiryJpaEntity inquiryEntity = getInquiryOrThrow(postId);

        InquiryAnswerJpaEntity answerEntity = inquiryAnswerJpaRepository.findByInquiryId(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_ANSWER_NOT_FOUND));

        answerEntity.update(request.title(), request.content());
        inquiryAnswerJpaRepository.save(answerEntity);

        answerAttachmentService.deleteAttachment(answerEntity);
        answerAttachmentService.createAttachment(request.attachments(), answerEntity);
    }

    private InquiryJpaEntity getInquiryOrThrow(Long postId) {
        return inquiryJpaRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_NOT_FOUND));
    }


}
