package life.mosu.mosuserver.application.school;


import java.util.List;
import life.mosu.mosuserver.domain.school.SchoolJpaRepository;
import life.mosu.mosuserver.presentation.school.dto.SchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SchoolService {

    private final SchoolJpaRepository schoolJpaRepository;

    public List<SchoolResponse> getSchools() {
        return schoolJpaRepository.findAll()
                .stream()
                .map(SchoolResponse::from)
                .toList();
    }
}
