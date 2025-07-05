package life.mosu.mosuserver.application.admin;

import life.mosu.mosuserver.domain.admin.StudentQueryRepositoryImpl;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentQueryRepositoryImpl studentQueryRepository;

    public Page<StudentListResponse> getStudents(String name, String phone, String order, Pageable pageable) {
        return studentQueryRepository.searchAllStudents(name, phone, order, pageable);
    }
}
