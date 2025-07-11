package life.mosu.mosuserver.presentation.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.event.dto.EventRequest;
import life.mosu.mosuserver.presentation.event.dto.EventResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Event API", description = "이벤트 관련 API 명세")
public interface EventControllerDocs {

    @Operation(summary = "이벤트 등록", description = "관리자가 새로운 이벤트를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이벤트 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> createEvent(
            @Parameter(description = "이벤트 생성 요청") @Valid @RequestBody EventRequest request
    );

    @Operation(summary = "이벤트 목록 조회", description = "전체 이벤트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = EventResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<List<EventResponse>>> getEvents();

    @Operation(summary = "이벤트 상세 조회", description = "특정 이벤트의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = EventResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<EventResponse>> getEventDetail(
            @Parameter(name = "eventId", description = "조회할 이벤트의 ID", in = ParameterIn.PATH)
            @PathVariable Long eventId
    );

    @Operation(summary = "이벤트 수정", description = "관리자가 특정 이벤트 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 수정 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateEvent(
            @Parameter(name = "eventId", description = "수정할 이벤트의 ID", in = ParameterIn.PATH)
            @PathVariable Long eventId,
            @Parameter(description = "이벤트 수정 요청")
            @Valid @RequestBody EventRequest request
    );

    @Operation(summary = "이벤트 삭제", description = "관리자가 특정 이벤트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 삭제 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteEvent(
            @Parameter(name = "eventId", description = "삭제할 이벤트의 ID", in = ParameterIn.PATH)
            @PathVariable Long eventId
    );
}