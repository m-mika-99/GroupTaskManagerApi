package com.example.GroupTaskManagerApi.presentation.restapi.v1.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema
public record LoginRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
