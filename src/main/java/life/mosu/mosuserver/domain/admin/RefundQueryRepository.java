package life.mosu.mosuserver.domain.admin;

import java.util.List;
import life.mosu.mosuserver.presentation.admin.dto.RefundListResponse;
import org.springframework.data.domain.Pageable;

public interface RefundQueryRepository {

    List<RefundListResponse> searchAllRefunds(Pageable pageable);
}
