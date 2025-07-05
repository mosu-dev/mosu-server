package life.mosu.mosuserver.domain.admin;

import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentQueryRepository {

    Page<StudentListResponse> searchAllStudents(StudentFilter filter, Pageable pageable);
}
