package life.mosu.mosuserver.application.school;

import java.util.List;
import life.mosu.mosuserver.domain.school.SchoolApplicationProjection;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.domain.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolQuotaCacheManager {

    private static final String REDIS_KEY_SCHOOL_MAX_CAPACITY = "school:max_capacity:";
    private static final String REDIS_KEY_SCHOOL_CURRENT_APPLICATIONS = "school:current_applications:";

    private final RedisTemplate<String, Long> redisTemplate;
    private final SchoolRepository schoolRepository;

    public void cacheSchoolMaxCapacities() {
        List<SchoolJpaEntity> schools = schoolRepository.findAll();
        for (SchoolJpaEntity school : schools) {
            String key = REDIS_KEY_SCHOOL_MAX_CAPACITY + school.getSchoolName();
            redisTemplate.opsForValue().set(key, school.getCapacity());
        }
    }

    public void cacheSchoolCurrentApplicationCounts() {
        List<SchoolApplicationProjection> schoolApplications = schoolRepository.countBySchoolNameGroupBy();
        for (SchoolApplicationProjection projection : schoolApplications) {
            String key = REDIS_KEY_SCHOOL_CURRENT_APPLICATIONS + projection.schoolName();
            redisTemplate.opsForValue().set(key, projection.count());
        }
    }

    public Long getSchoolApplicationCounts(String schoolName) {
        return redisTemplate.opsForValue()
                .get(REDIS_KEY_SCHOOL_CURRENT_APPLICATIONS + schoolName);
    }

    public Long getSchoolCapacities(String schoolName) {
        return redisTemplate.opsForValue()
                .get(REDIS_KEY_SCHOOL_MAX_CAPACITY + schoolName);
    }

    public void increaseApplicationCount(String schoolName) {
        String key = REDIS_KEY_SCHOOL_CURRENT_APPLICATIONS + schoolName;
        redisTemplate.opsForValue().increment(key);
    }

    public void decreaseApplicationCount(String schoolName) {
        String key = REDIS_KEY_SCHOOL_CURRENT_APPLICATIONS + schoolName;
        Long currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue > 0) {
            redisTemplate.opsForValue().decrement(key);
        }
    }

    public void preloadSchoolData() {
        cacheSchoolMaxCapacities();
        cacheSchoolCurrentApplicationCounts();
    }
}