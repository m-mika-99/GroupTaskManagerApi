package com.example.GroupTaskManagerApi.presentation.restapi.v1.shared.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PagingInfoSchema",
        description = "オフセットページング情報"
)
public record PagingInfoSchema(
        @Schema(description = "現在ページ番号")
        @JsonProperty("current_page")
        int currentPage,

        @Schema(description = "ページ当たり表示数")
        @JsonProperty("items_per_page")
        int itemsPerPage,

        @Schema(description = "総数")
        @JsonProperty("max_items")
        int maxItems,

        @Schema(description = "最大ページ番号")
        @JsonProperty("max_page")
        int maxPage,

        @Schema(description = "前ページ有")
        @JsonProperty("has_previous")
        boolean hasPrevious,

        @Schema(description = "後ページ有")
        @JsonProperty("has_next")
        boolean hasNext
) {
}
