package com.example.GroupTaskManagerApi.presentation.restapi.v1.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(
        name = "UserSchema",
        description = "グループ内メンバ"
)
public record UserResponse(
        @Schema(
                description = "ユーザID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("user_id")
        @NotNull
        UUID userId,

        @Schema(
                description = "表示名",
                example = "田中太郎"
        )
        @JsonProperty("display_name")
        @NotNull
        String displayName
) {
}
