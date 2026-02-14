package com.example.GroupTaskManagerApi.presentation.restapi.v1.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record LoginResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
