package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
}

