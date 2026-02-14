package com.example.GroupTaskManagerApi.presentation.restapi.v1.group.response;

import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.MemberSchema;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(
        name = "GroupDetailResponse",
        description = "グループおよび所属メンバ"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GroupDetailResponse(
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
                example = "作業チーム",
                maxLength = 64
        )
        @JsonProperty("name")
        @NotBlank
        @Size(max = 64)
        String name,

        @Schema(
                description = "説明",
                example = "マイスペシャルチーム"
        )
        @JsonProperty("description")
        String description,

        @ArraySchema(
                arraySchema = @Schema(description = "参加メンバ"),
                schema = @Schema(implementation = MemberSchema.class)
        )
        @JsonProperty("members")
        @NotNull
        List<MemberSchema> members
) {
}
