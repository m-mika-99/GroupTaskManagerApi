package com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "AssignmentSchema",
        description = "タスク割り当て"
)
public record AssignmentSchema(
        @Schema(
                description = "メンバID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("member_id")
        @NotNull
        UUID memberId,

        @Schema(
                description = "割当ステータス",
                example = "DONE"
        )
        @JsonProperty("status")
        @NotNull
        @Pattern(regexp = "IN_PROGRESS|DONE")
        String status,

        @Schema(
                description = "完了日時",
                type = "string",
                format = "date-time",
                nullable = true,
                example = "2026-02-20T23:59:59"
        )
        @JsonProperty("done_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime doneAt
) {
}
