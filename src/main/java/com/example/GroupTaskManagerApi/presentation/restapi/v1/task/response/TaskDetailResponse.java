package com.example.GroupTaskManagerApi.presentation.restapi.v1.task.response;


import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.AssignmentSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.MemberSchema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(
        name = "TaskDetailResponse",
        description = "タスクおよび割り当てられているユーザ一覧"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDetailResponse(

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

        @Schema(
                description = "タスク発行者",
                nullable = true
        )
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

        @ArraySchema(
                arraySchema = @Schema(description = "このタスクに紐づく割当一覧"),
                schema = @Schema(implementation = AssignmentSchema.class)
        )
        @JsonProperty("assignments")
        @NotNull
        List<AssignmentSchema> assignments

) {
}
