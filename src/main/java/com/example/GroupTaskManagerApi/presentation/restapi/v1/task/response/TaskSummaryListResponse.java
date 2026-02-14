package com.example.GroupTaskManagerApi.presentation.restapi.v1.task.response;

import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.PagingInfoSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.TaskSummarySchema;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(
        name = "TaskSummaryListResponse",
        description = "タスクサマリ一覧"
)
public record TaskSummaryListResponse(

        @Schema(description = "ページング情報")
        @JsonProperty("paging")
        @NotNull
        PagingInfoSchema paging,

        @ArraySchema(
                arraySchema = @Schema(description = "タスクサマリ一覧"),
                schema = @Schema(implementation = TaskSummarySchema.class)
        )
        @JsonProperty("task_summaries")
        @NotNull
        List<TaskSummarySchema> taskSummaries
) {
}
