package life.mosu.mosuserver.domain.admin;

import life.mosu.mosuserver.presentation.admin.dto.RefundListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RefundQueryRepository {

    Page<RefundListResponse> searchAllRefunds(Pageable pageable);
}
