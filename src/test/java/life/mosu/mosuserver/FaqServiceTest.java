package life.mosu.mosuserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import life.mosu.mosuserver.application.faq.FaqAttachmentService;
import life.mosu.mosuserver.application.faq.FaqService;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqJpaRepository;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FaqServiceTest {

    @Mock
    private FaqJpaRepository faqJpaRepository;
    @Mock
    private FaqAttachmentService faqAttachmentService;

    private FaqService faqService;

    @BeforeEach
    void setUp() {

        faqService = new FaqService(
                faqJpaRepository,
                faqAttachmentService
        );
    }

    @Test
    void 파일등록_성공() {
        // given
        FaqCreateRequest request = mock(FaqCreateRequest.class);
        FaqJpaEntity savedEntity = mock(FaqJpaEntity.class);

        when(faqJpaRepository.save(any())).thenReturn(savedEntity);
        when(request.toEntity()).thenReturn(savedEntity);
        when(savedEntity.getId()).thenReturn(1L);

        // when
        faqService.createFaq(request);

        // then
        verify(faqJpaRepository, atLeastOnce()).save(any());
        assertEquals(1L, savedEntity.getId());
    }
}
