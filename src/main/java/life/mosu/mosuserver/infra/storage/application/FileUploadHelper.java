package life.mosu.mosuserver.infra.storage.application;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.infra.storage.domain.FileMoveFailLog;
import life.mosu.mosuserver.infra.storage.domain.FileMoveFailLogRepository;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileUploadHelper {
    private final S3Service s3Service;
    private final FileMoveFailLogRepository fileMoveFailLogRepository;
    private final FaqAttachmentRepository faqAttachmentRepository;
    private final ExecutorService executorService;

    public void moveToFolder(List<FileRequest> fileRequests, Long faqId, Folder folder) {
        for (FileRequest fileRequest : fileRequests) {

            CompletableFuture.runAsync(() -> {
                    s3Service.moveFile(fileRequest.s3Key(), folder);
                }, executorService)
                .exceptionally(ex -> {
                    fileMoveFailLogRepository.save(
                        FileMoveFailLog.of(faqId, fileRequest.s3Key(), folder));
                    throw new CustomRuntimeException(ErrorCode.FILE_UPLOAD_FAILED, ex);
                });

            faqAttachmentRepository.save(fileRequest.toAttachmentEntity(
                fileRequest.fileName(),
                fileRequest.s3Key(),
                faqId
            ));
        }
    }
}
