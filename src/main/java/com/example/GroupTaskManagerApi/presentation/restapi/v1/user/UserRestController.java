package com.example.GroupTaskManagerApi.presentation.restapi.v1.user;

import com.example.GroupTaskManagerApi.application.user.UserApplicationService;
import com.example.GroupTaskManagerApi.domain.user.model.User;
import com.example.GroupTaskManagerApi.domain.user.model.UserId;
import com.example.GroupTaskManagerApi.infra.security.userdetail.CustomUserDetails;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User")
@SecurityRequirement(name = "bearerAuth")
@SuppressWarnings("unused") // Spring利用
public class UserRestController {

    private final UserApplicationService userService;

    @Autowired
    public UserRestController (UserApplicationService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(description = "自己情報取得")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<UserResponse> getMe (
            @AuthenticationPrincipal
            CustomUserDetails loginUser
    ) {
        UUID userId = loginUser.getUserId();
        User user = userService.findUserById(new UserId(userId));
        return ResponseEntity.ok(
                new UserResponse(userId, user.getDisplayName())
        );
    }

}
