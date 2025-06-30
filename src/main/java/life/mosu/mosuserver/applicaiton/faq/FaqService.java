package life.mosu.mosuserver.applicaiton.faq;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;
    private final FaqAttachmentRepository faqAttachmentRepository;
    private final S3Service s3Service;
    private final ExecutorService executorService;

    @Value("${s3.presigned-url-expiration-minutes}")
    private int durationTime;

    @Transactional
    public void createFaq(FaqCreateRequest request) {
        FaqJpaEntity faqEntity = faqRepository.save(request.toEntity());

        createAttachmentIfPresent(request, faqEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<FaqResponse> getFaqWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<FaqJpaEntity> faqPage = faqRepository.findAll(pageable);

        return faqPage.stream()
            .map(this::toFaqResponse)
            .toList();
    }


    @Transactional
    public void deleteFaq(Long faqId) {
        FaqJpaEntity faqEntity = faqRepository.findById(faqId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        faqRepository.delete(faqEntity);

        deleteAttachmentIfPresent(faqEntity);
    }


    private void createAttachmentIfPresent(FaqCreateRequest request, FaqJpaEntity faqEntity) {
        if (request.file() == null || request.file().isEmpty()) {
            return;
        }

        Long faqId = faqEntity.getId();

        List<CompletableFuture<Void>> futures = request.file().stream()
            .map(file -> CompletableFuture.runAsync(() -> {
                String s3Key = s3Service.uploadFile(file, Folder.FAQ);
                String fileName = file.getOriginalFilename();
                faqAttachmentRepository.save(request.toAttachmentEntity(fileName, s3Key, faqId));
            }, executorService))
            .toList();

        try{
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }
        catch (Exception e) {
            throw new CustomRuntimeException(ErrorCode.FILE_UPLOAD_FAILED);
        }

    }

    private FaqResponse toFaqResponse(FaqJpaEntity faq) {
        List<FaqAttachmentJpaEntity> attachments = faqAttachmentRepository.findAllByFaqId(faq.getId());
        List<FaqResponse.AttachmentResponse> attachmentResponses = toAttachmentResponses(attachments);
        return FaqResponse.of(faq, attachmentResponses);
    }

    private List<FaqResponse.AttachmentResponse> toAttachmentResponses(List<FaqAttachmentJpaEntity> attachments) {
        return attachments.stream()
            .map(attachment -> new FaqResponse.AttachmentResponse(
                attachment.getFileName(),
                s3Service.getPreSignedUrl(
                    attachment.getS3Key(),
                    Duration.ofMinutes(durationTime)
                )
            ))
            .toList();
    }

    //TODO: S3Service에서 SoftDelete 적용 된 파일들에 대해 따로 분리하는 로직 필요할 듯
    private void deleteAttachmentIfPresent(FaqJpaEntity entity) {
        List<FaqAttachmentJpaEntity> attachments = faqAttachmentRepository.findAllByFaqId(entity.getId());
        faqAttachmentRepository.deleteAll(attachments);
    }
}
