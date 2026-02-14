package com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(
        name = "GroupSummarySchema",
        description = "グループサマリ"
)
public record GroupSummarySchema(
        @Schema(
                description = "グループID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid"
        )
        @JsonProperty("id")
        @NotNull
        UUID id,

        @Schema(
                description = "名前",
                example = "作業グループ",
                maxLength = 64
        )
        @JsonProperty("name")
        @NotBlank
        @Size(max = 64)
        String name,

        @Schema(
                description = "説明",
                example = "社内システムにて実績確定をお願いします"
        )
        @JsonProperty("description")
        String description
) {
}
