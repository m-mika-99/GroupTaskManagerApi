package com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Schema(
        name = "MemberSchema",
        description = "グループ内メンバ"
)
public record MemberSchema(
        @Schema(
                description = "メンバID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("member_id")
        @NotNull
        UUID memberId,

        @Schema(
                description = "表示名",
                example = "田中太郎"
        )
        @JsonProperty("display_name")
        @NotNull
        String displayName,

        @Schema(
                description = "グループ内ロール",
                example = "MEMBER"
        )
        @JsonProperty("role")
        @NotNull
        @Pattern(regexp = "MEMBER|ADMIN|OWNER")
        String role
) {
}
