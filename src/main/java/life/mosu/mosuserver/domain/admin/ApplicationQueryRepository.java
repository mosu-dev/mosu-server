package life.mosu.mosuserver.domain.admin;

import java.util.List;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationFilter;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationListResponse;
import life.mosu.mosuserver.presentation.admin.dto.SchoolLunchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationQueryRepository {

    Page<ApplicationListResponse> searchAllApplications(ApplicationFilter filter,
            Pageable pageable);

    List<ApplicationExcelDto> searchAllApplicationsForExcel();

    List<SchoolLunchResponse> searchAllSchoolLunches();
}
