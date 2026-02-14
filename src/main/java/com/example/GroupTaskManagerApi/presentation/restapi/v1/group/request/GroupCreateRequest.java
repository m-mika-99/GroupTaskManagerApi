package com.example.GroupTaskManagerApi.presentation.restapi.v1.group.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "GroupCreateRequest",
        description = "グループ新規作成リクエスト"
)
public record GroupCreateRequest(
        @Schema(
                description = "名前",
                example = "マイグループ",
                maxLength = 64
        )
        @JsonProperty("name")
        @NotBlank
        @Size(max = 64)
        String name,

        @Schema(
                description = "説明",
                example = "スペシャルなマイグループです"
        )
        @JsonProperty("description")
        String description
) {
}
