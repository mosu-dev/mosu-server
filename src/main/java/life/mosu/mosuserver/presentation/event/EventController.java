package life.mosu.mosuserver.presentation.event;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.event.EventService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.event.dto.EventRequest;
import life.mosu.mosuserver.presentation.event.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @PostMapping
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> createEvent(
            @Valid @RequestBody EventRequest request) {
        eventService.createEvent(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "이벤트 등록 성공"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponseWrapper<List<EventResponse>>> getEvents(
    ) {
        List<EventResponse> responses = eventService.getEvents();
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "이벤트 조회 성공", responses));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseWrapper<EventResponse>> getEventDetail(
            @PathVariable Long eventId) {
        EventResponse event = eventService.getEventDetail(eventId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "이벤트 상세 조회 성공", event));
    }

    @PutMapping("/{eventId}")
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequest request
    ) {
        eventService.update(request, eventId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "이벤트 수정 성공"));
    }

    @DeleteMapping("/{eventId}")
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "이벤트 삭제 성공"));
    }

}
