package com.example.GroupTaskManagerApi.presentation.restapi.v1.task.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(
        name = "TaskCreateRequest",
        description = "タスク新規作成リクエスト"
)
public record TaskCreateRequest(
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

        @Schema(
                description = "発行者メンバID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("issuer_member_id")
        @NotNull
        UUID issuerMemberId,

        @Schema(
                description = "〆切日時",
                type = "string",
                format = "date-time",
                example = "2026-02-20T23:59:59"
        )
        @JsonProperty("deadline_at")
        @NotNull
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime deadlineAt,

        @ArraySchema(
                arraySchema = @Schema(description = "このタスクに紐づけるメンバIDリスト"),
                schema = @Schema(
                        description = "メンバID",
                        example = "550e8400-e29b-41d4-a716-446655440000",
                        format = "uuid"
                )
        )
        @JsonProperty("assignee_member_ids")
        @NotNull
        List<UUID> assigneeMemberIds
) {
}
