package com.example.GroupTaskManagerApi.presentation.restapi.v1.auth;

import com.example.GroupTaskManagerApi.application.auth.AuthApplicationService;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.auth.request.LoginRequest;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.auth.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthRestController {

    private final AuthApplicationService authService;

    @Autowired
    public AuthRestController (AuthApplicationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(description = "ログイン")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ログイン成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<LoginResponse> login (
            @RequestBody
            @Valid
            LoginRequest request
    ) {
        String token = authService.login(
                request.email(),
                request.password()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }

}
