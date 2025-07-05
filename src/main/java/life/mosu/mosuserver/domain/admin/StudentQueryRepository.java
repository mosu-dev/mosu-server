package life.mosu.mosuserver.domain.admin;

import org.springframework.data.domain.Pageable;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import org.springframework.data.domain.Page;

public interface StudentQueryRepository {
    Page<StudentListResponse> searchAllStudents(String name, String phone, String order, Pageable pageable);
}
