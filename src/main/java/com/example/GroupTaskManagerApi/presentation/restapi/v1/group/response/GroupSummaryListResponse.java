package com.example.GroupTaskManagerApi.presentation.restapi.v1.group.response;

import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.GroupSummarySchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.PagingInfoSchema;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(
        name = "GroupSummaryListResponse",
        description = "グループサマリ一覧"
)
public record GroupSummaryListResponse(
        @Schema(description = "ページング情報")
        @JsonProperty("paging")
        @NotNull
        PagingInfoSchema paging,

        @ArraySchema(
                arraySchema = @Schema(description = "グループサマリ一覧"),
                schema = @Schema(implementation = GroupSummarySchema.class)
        )
        @JsonProperty("group_summaries")
        @NotNull
        List<GroupSummarySchema> groupSummaries
) {
}
