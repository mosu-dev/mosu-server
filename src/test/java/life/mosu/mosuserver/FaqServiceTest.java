package life.mosu.mosuserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import life.mosu.mosuserver.application.faq.FaqService;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqRepository;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class FaqServiceTest {

    @Mock private FaqRepository faqRepository;
    @Mock private FaqAttachmentRepository faqAttachmentRepository;
    @Mock private S3Service s3Service;

    private ExecutorService realExecutorService;
    private FaqService faqService;

    @BeforeEach
    void setUp() {
        realExecutorService = Executors.newSingleThreadExecutor();

        faqService = new FaqService(
                faqRepository,
                faqAttachmentRepository,
                s3Service,
                realExecutorService
        );
    }

    @Test
    void FAQ_생성_요청시_FAQ_저장_그리고_파일_업로드_해야함() {
        // given
        MultipartFile fileMock = mock(MultipartFile.class);
        FaqCreateRequest request = new FaqCreateRequest("질문", "답변", 1L, List.of(fileMock));
        FaqJpaEntity faqEntity = request.toEntity();

        when(faqRepository.save(any())).thenReturn(faqEntity);
        when(s3Service.uploadFile(any(), eq(Folder.FAQ))).thenReturn("s3-key");

        // when
        faqService.createFaq(request);

        verify(faqRepository).save(any());
        verify(s3Service, atLeastOnce()).uploadFile(any(), eq(Folder.FAQ));
        verify(faqAttachmentRepository, atLeastOnce()).save(any());
    }
}
