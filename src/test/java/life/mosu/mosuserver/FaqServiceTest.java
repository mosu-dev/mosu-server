package life.mosu.mosuserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private FaqService faqService;

    @BeforeEach
    void setUp() {

        faqService = new FaqService(
                faqRepository,
                faqAttachmentRepository,
                s3Service,
                null
        );
    }

    @Test
    void 파일등록_성공() {
        // given
        FaqCreateRequest request = mock(FaqCreateRequest.class);
        FaqJpaEntity savedEntity = mock(FaqJpaEntity.class);

        when(faqRepository.save(any())).thenReturn(savedEntity);
        when(request.toEntity()).thenReturn(savedEntity);
        when(savedEntity.getId()).thenReturn(1L);

        // when
        faqService.createFaq(request);

        // then
        verify(faqRepository, atLeastOnce()).save(any());
        assertEquals(1L, savedEntity.getId());
    }
}
