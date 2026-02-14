package com.example.GroupTaskManagerApi.presentation.restapi.v1.group;

import com.example.GroupTaskManagerApi.presentation.restapi.v1.group.request.GroupCreateRequest;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.group.response.GroupDetailResponse;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.group.response.GroupSummaryListResponse;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.MemberSchema;
import com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema.PagingInfoSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@Tag(name = "Group")
public class GroupRestController {

    @GetMapping
    @Operation(description = "参加グループサマリ一覧取得")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupSummaryListResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<GroupSummaryListResponse> getGroups () {
        return ResponseEntity.ok(
                new GroupSummaryListResponse(
                        new PagingInfoSchema(1, 10, 23, 3, false, true),
                        List.of()
                )
        );
    }

    @PostMapping
    @Operation(description = "グループ新規作成")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "作成成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupDetailResponse.class)
                    )
            ),
//            @ApiResponse(responseCode = "400", description = "リクエスト不正"),
//            @ApiResponse(responseCode = "401", description = "認証"),
//            @ApiResponse(responseCode = "404", description = "グループ、タスクが不存在"),
//            @ApiResponse(responseCode = "500", description = "サーバエラー"),
    })
    public ResponseEntity<GroupDetailResponse> create (
            @RequestBody
            @Valid
            GroupCreateRequest request
    ) {
        MemberSchema member = new MemberSchema(
                UUID.randomUUID(),
                "所有太郎",
                "OWNER"
        );
        return ResponseEntity.ok(
                new GroupDetailResponse(
                        UUID.randomUUID(),
                        "",
                        "",
                        List.of(member)
                )
        );
    }


}
