package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@AllArgsConstructor
@Getter
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String phone;
    private String email;
}