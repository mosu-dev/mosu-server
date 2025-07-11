package life.mosu.mosuserver.application.school;


import java.util.List;
import life.mosu.mosuserver.domain.school.SchoolJpaRepository;
import life.mosu.mosuserver.presentation.school.dto.SchoolRegistrationRequest;
import life.mosu.mosuserver.presentation.school.dto.SchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolJpaRepository schoolJpaRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<SchoolResponse> getSchools() {
        return schoolJpaRepository.findAll()
                .stream()
                .map(SchoolResponse::from)
                .toList();
    }

    @Transactional
    public void registerSchool(SchoolRegistrationRequest request) {
        schoolJpaRepository.save(request.toEntity());
    }
}
