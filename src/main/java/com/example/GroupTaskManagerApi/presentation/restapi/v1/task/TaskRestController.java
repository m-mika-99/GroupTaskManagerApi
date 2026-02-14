package com.example.GroupTaskManagerApi.presentation.restapi.v1.task;

import com.example.GroupTaskManagerApi.domain.task.model.TaskId;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.AssignmentSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.MemberSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.PagingInfoSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.TaskSummarySchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.task.request.TaskCreateRequest;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.task.response.TaskDetailResponse;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.task.response.TaskSummaryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups/{grp-id}/tasks")
@Tag(name = "Task")
public class TaskRestController {

    @GetMapping
    @Operation(description = "グループ内タスクサマリ一覧取得")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskSummaryListResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<TaskSummaryListResponse> getTasksInGroup (
            @RequestParam(defaultValue = "1")
            int page,

            @RequestParam(defaultValue = "20")
            int size
    ) {
        TaskSummarySchema summary1 = new TaskSummarySchema(
                UUID.randomUUID(),
                "サンプルタスクだよー",
                null,
                new MemberSchema(
                        UUID.randomUUID(),
                        "発行者太郎",
                        "MEMBER"
                ),
                LocalDateTime.now(),
                LocalDateTime.now(),
                8,
                4,
                true,
                false
        );

        return ResponseEntity.ok(
                new TaskSummaryListResponse(
                        new PagingInfoSchema(1, 10, 23, 3, false, true),
                        List.of(summary1)
                )
        );
    }


    @PostMapping
    @Operation(description = "新しいタスクを発行")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "作成成功",
                    headers = @Header(
                            name = "Location",
                            description = "作成されたタスク詳細のURL",
                            schema = @Schema(type = "string", example = "/api/v1/groups/550e8400-e29b-41d4-a716-446655440000/tasks/550e8400-e29b-41d4-a716-446655440000")
                    ),
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDetailResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<TaskDetailResponse> getTasksInGroup (
            @PathVariable("grp-id")
            @Parameter(
                    name = "grp-id",
                    description = "グループID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID groupId,

            @RequestBody
            @Valid
            TaskCreateRequest request
    ) {
        AssignmentSchema assign1 = new AssignmentSchema(
                UUID.randomUUID(),
                "IN_PROGRESS",
                null
        );
        AssignmentSchema assign2 = new AssignmentSchema(
                UUID.randomUUID(),
                "DONE",
                LocalDateTime.now()
        );
        TaskDetailResponse res = new TaskDetailResponse(
                UUID.randomUUID(),
                "サンプルのタスク",
                "サンプルのタスクの説明うんぬんかんぬん",
                new MemberSchema(
                        UUID.randomUUID(),
                        "田中花子",
                        "MEMBER"
                ),
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(assign1, assign2)
        );
        TaskId taskId = TaskId.createNew();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskId.value().toString())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{tsk-id}")
    @Operation(description = "ID指定でタスクの詳細取得")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDetailResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<TaskDetailResponse> getTaskById (
            @PathVariable("grp-id")
            @Parameter(
                    name = "grp-id",
                    description = "グループID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID groupId,

            @PathVariable("tsk-id")
            @Parameter(
                    name = "tsk-id",
                    description = "タスクID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID taskId
    ) {
        AssignmentSchema assign1 = new AssignmentSchema(
                UUID.randomUUID(),
                "IN_PROGRESS",
                null
        );
        AssignmentSchema assign2 = new AssignmentSchema(
                UUID.randomUUID(),
                "DONE",
                LocalDateTime.now()
        );
        TaskDetailResponse res = new TaskDetailResponse(
                UUID.randomUUID(),
                "サンプルのタスク",
                "サンプルのタスクの説明うんぬんかんぬん",
                new MemberSchema(
                        UUID.randomUUID(),
                        "田中花子",
                        "MEMBER"
                ),
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(assign1, assign2)
        );
        return ResponseEntity.ok(res);
    }


    @PatchMapping("/{tsk-id}/done")
    @Operation(description = "ID指定のタスクを完了にする")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功"),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<Void> done (
            @PathVariable("grp-id")
            @Parameter(
                    name = "grp-id",
                    description = "グループID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID groupId,

            @PathVariable("tsk-id")
            @Parameter(
                    name = "tsk-id",
                    description = "タスクID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID taskId
    ) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tsk-id}/archive")
    @Operation(description = "ID指定のタスクをアーカイブする")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功"),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<Void> archive (
            @PathVariable("grp-id")
            @Parameter(
                    name = "grp-id",
                    description = "グループID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID groupId,

            @PathVariable("tsk-id")
            @Parameter(
                    name = "tsk-id",
                    description = "タスクID",
                    required = true,
                    in = ParameterIn.PATH,
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            UUID taskId
    ) {
        return ResponseEntity.noContent().build();
    }
}
