package life.mosu.mosuserver.infra.storage;

import java.util.concurrent.ExecutorService;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.infra.storage.domain.FileMoveFailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileUploadHelper {

    private final S3Service s3Service;
    private final FileMoveFailLogRepository fileMoveFailLogRepository;
    private final FaqAttachmentRepository faqAttachmentRepository;
    private final ExecutorService executorService;

    public void updateTag(String s3Key) {
        s3Service.updateFileTagToActive(s3Key);
    }


}
