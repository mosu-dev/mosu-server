package life.mosu.mosuserver.applicaiton.faq;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;
    private final FaqAttachmentRepository faqAttachmentRepository;
    private final S3Service s3Service;
    private final ExecutorService executorService;

    @Transactional
    public void createFaq(FaqCreateRequest request) {
        FaqJpaEntity entity = faqRepository.save(request.toEntity());

        createAttachmentIfPresent(request, entity);
    }

    @Transactional
    public void deleteFaq(Long faqId) {
        FaqJpaEntity entity = faqRepository.findById(faqId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        faqRepository.delete(entity);

        deleteAttachmentIfPresent(entity);
    }


    private void createAttachmentIfPresent(FaqCreateRequest request, FaqJpaEntity entity) {
        if (request.file() == null) return;

        Long faqId = entity.getId();

        List<CompletableFuture<Void>> futures = request.file().stream()
            .map(file -> CompletableFuture.runAsync(() -> {

                String s3Key = s3Service.uploadFile(file, Folder.FAQ);
                String fileName = file.getOriginalFilename();
                faqAttachmentRepository.save(request.toAttachmentEntity(fileName, s3Key, faqId));

            }, executorService))
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void deleteAttachmentIfPresent(FaqJpaEntity entity) {
        List<FaqAttachmentJpaEntity> attachments = faqAttachmentRepository.findAllByFaqId(entity.getId());
        faqAttachmentRepository.deleteAll(attachments);
    }
}
