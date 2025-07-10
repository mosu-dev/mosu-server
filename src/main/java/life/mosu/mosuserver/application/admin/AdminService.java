package life.mosu.mosuserver.application.admin;

import java.util.List;
import life.mosu.mosuserver.domain.admin.ApplicationQueryRepositoryImpl;
import life.mosu.mosuserver.domain.admin.RefundQueryRepositoryImpl;
import life.mosu.mosuserver.domain.admin.StudentQueryRepositoryImpl;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationFilter;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationListResponse;
import life.mosu.mosuserver.presentation.admin.dto.RefundListResponse;
import life.mosu.mosuserver.presentation.admin.dto.SchoolLunchResponse;
import life.mosu.mosuserver.presentation.admin.dto.StudentExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentQueryRepositoryImpl studentQueryRepository;
    private final ApplicationQueryRepositoryImpl applicationQueryRepository;
    private final RefundQueryRepositoryImpl refundQueryRepository;

    public Page<StudentListResponse> getStudents(StudentFilter filter, Pageable pageable) {
        return studentQueryRepository.searchAllStudents(filter, pageable);
    }

    public List<StudentExcelDto> getStudentExcelData() {
        return studentQueryRepository.searchAllStudentsForExcel();
    }

    public List<SchoolLunchResponse> getLunchCounts() {
        return applicationQueryRepository.searchAllSchoolLunches();
    }

    public Page<ApplicationListResponse> getApplications(ApplicationFilter filter,
            Pageable pageable) {
        return applicationQueryRepository.searchAllApplications(filter, pageable);
    }

    public List<ApplicationExcelDto> getApplicationExcelData() {
        return applicationQueryRepository.searchAllApplicationsForExcel();
    }

    public List<RefundListResponse> getRefunds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return refundQueryRepository.searchAllRefunds(pageable);
    }

}
