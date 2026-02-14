package com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "TaskSummarySchema",
        description = "タスクサマリ"
)
public record TaskSummarySchema(
        @Schema(
                description = "タスクID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("id")
        @NotNull
        UUID id,

        @Schema(
                description = "タイトル",
                example = "３月締め作業",
                maxLength = 64
        )
        @JsonProperty("title")
        @NotBlank
        @Size(max = 64)
        String title,

        @Schema(
                description = "説明",
                example = "社内システムにて実績確定をお願いします"
        )
        @JsonProperty("description")
        String description,

        @Schema(description = "タスク発行者")
        @JsonProperty("issuer")
        @NotNull
        MemberSchema issuer,

        @Schema(
                description = "発行日時",
                type = "string",
                format = "date-time",
                example = "2026-02-20T23:59:59"
        )
        @JsonProperty("issued_at")
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime issuedAt,

        @Schema(
                description = "〆切日時",
                type = "string",
                format = "date-time",
                example = "2026-02-20T23:59:59"
        )
        @JsonProperty("deadline_at")
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime deadlineAt,

        @Schema(
                description = "割当人数",
                example = "23"
        )
        @JsonProperty("assignee_count")
        @NotNull
        int assigneeCount,

        @Schema(
                description = "割当中完了人数",
                example = "12"
        )
        @JsonProperty("assignee_done_count")
        @NotNull
        int assigneeDoneCount,

        @Schema(
                description = "自分が割当たっているか",
                example = "true"
        )
        @JsonProperty("is_assigned")
        @NotNull
        boolean isAssigned,

        @Schema(
                description = "自分が完了しているか",
                example = "true"
        )
        @JsonProperty("is_done")
        @NotNull
        boolean isDone
) {
}
