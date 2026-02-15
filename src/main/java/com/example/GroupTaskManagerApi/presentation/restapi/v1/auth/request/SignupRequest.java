package com.example.GroupTaskManagerApi.presentation.restapi.v1.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "SignupRequest",
        description = "新規ユーザ登録リクエスト"
)
public record SignupRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        @JsonProperty("display_name")
        String displayName
) {
}
