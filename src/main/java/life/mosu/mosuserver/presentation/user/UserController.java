package life.mosu.mosuserver.presentation.user;

import life.mosu.mosuserver.application.user.UserService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/customer-key")
    public ResponseEntity<ApiResponseWrapper<CustomerKeyResponse>> getCustomerKey(
            @RequestParam Long userId
    ) {
        String customerKey = userService.getCustomerKey(userId);

        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "CustomerKey 조회 성공",
                CustomerKeyResponse.from(customerKey)));
    }

}
