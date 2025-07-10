package life.mosu.mosuserver.global.runner;

import life.mosu.mosuserver.application.school.SchoolQuotaCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationSchoolPreWarmRunner {

    private final SchoolQuotaCacheManager schoolQuotaCacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void preloadSchoolData() {
        schoolQuotaCacheManager.preloadSchoolData();
    }
}
